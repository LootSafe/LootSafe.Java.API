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
    private final String APIURL;

    public NodeHandler(String APIURL) {
        this.APIURL = APIURL;
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, 30000)
                .property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(JsonGenerator.PRETTY_PRINTING, true);

        webTarget = ClientBuilder
                .newBuilder()
                //.sslContext(ssl)
                .newClient(clientConfig)
                .target(APIURL);
        U.info("Constructed a new client for " + webTarget.getUri().toString());
    }


    public String genericRequest(String formedNodeString) {
        U.debug(formedNodeString);
        U.debug("Trying to retrieve info from " + APIURL + "/" + formedNodeString);
        try {
            Response response = webTarget
                    .path("/" + formedNodeString)
                    .request(MediaType.APPLICATION_JSON)
                    .header("key", ServiceProvider.getInstance().getPrivateKey())
                    .get();
            U.debug("Status: " + response.getStatus());
            if (!(response.getStatus() == 404)) {
                JsonObject responseJson = response.readEntity(JsonObject.class);

                return responseJson.getString("message");
            }
        } catch (Exception e) {
            U.error("Error Contacting LootSafe Servers");
        }
        return ("Error Contacting LootSafe Servers");
    }

}
