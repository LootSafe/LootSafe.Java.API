package io.lootsafe.api.Requests;

import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */

public class NodeHandler {

    private final WebTarget webTarget;
    private final String apiUrl;
    private final String apiKey;
    private final String otp;

    public NodeHandler(String apiUrl, String apiKey, String otp) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.otp = otp;
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, 30000)
                .property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(JsonGenerator.PRETTY_PRINTING, true);

        webTarget = ClientBuilder
                .newBuilder()
                //.sslContext(ssl)
                .newClient(clientConfig)
                .target(apiUrl);
        U.info("Constructed a new client for " + webTarget.getUri().toString());
    }


    public JsonObject genericRequest(String formedNodeString) {
        U.debug(formedNodeString);
        U.debug("Trying to retrieve info from " + apiUrl + "/" + formedNodeString);
        try {
            Response response = webTarget
                    .path("/" + formedNodeString)
                    .request(MediaType.APPLICATION_JSON)
                    .header("key", apiKey)
                    .header("otp", otp)
                    .get();
            U.debug("Status:" + response.getStatus());
            if (response.getStatus() == 200) {
                JsonObject responseJson = response.readEntity(JsonObject.class);
                return responseJson;
            } else {
                U.error("There was an error while processing your request!");
                U.error("Status: " + response.getStatus() + " Message: " + response.getStatusInfo());
                return null;
            }
        } catch (Exception e) {
            U.error("Error Contacting LootSafe Servers");
        }
        return null;
    }

    public boolean test(){
        return true;
    }

}
