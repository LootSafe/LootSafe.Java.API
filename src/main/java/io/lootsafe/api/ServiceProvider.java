package io.lootsafe.api;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */

/**
 * This class will be the entry for all Java Clients. To Construct it will need to be fed some important
 * components vital to it's existence.
 * <p>
 *
 */
public class ServiceProvider {

    private String privateKey = "";
    private String APIHost = "";
    private String APIVersion = "";
    private String APIURL;

    private static ServiceProvider instance;
    private Client clientInterface;
    private boolean working = false;

    private ServiceProvider(ClientBuilder builder) {
        this.clientInterface = builder.clientInterface;
        privateKey = builder.privateKey;
        APIHost = builder.APIHost;
        APIVersion = builder.APIVersion;
        APIURL = APIHost + "/v" + APIVersion + "/";
        instance = this;
    }


    /**
     * Use to get the built URL
     * @return
     */

    public String getURL(){
        return APIURL;
    }

    /**
     * Use to get the currently running instance of Service Provider
     * @return
     */
    public static ServiceProvider getInstance(){
        return instance;
    }

    /**
     * This is a builder which provides a service provider for the client.
     */
    public static class ClientBuilder{
        private Client clientInterface;
        private String privateKey;
        private String APIHost;
        private String APIVersion;

        public ClientBuilder() {}

        public ClientBuilder withClient(Client clientInterface){
            this.clientInterface = clientInterface;
            return this;
        }

        public ClientBuilder withPrivateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public ClientBuilder withHost(String APIhost) {
            this.APIHost = APIhost;
            return this;
        }

        public ClientBuilder withVersion(String APIVersion) {
            this.APIVersion = APIVersion;
            return this;
        }

        public ServiceProvider build(){
            return new ServiceProvider(this);
        }
    }






}
