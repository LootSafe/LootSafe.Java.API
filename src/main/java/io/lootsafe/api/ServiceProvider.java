package io.lootsafe.api;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */

import io.lootsafe.api.Requests.NodeHandler;

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
    private NodeHandler nh;
    private boolean working = false;
    private boolean debug = true;

    private ServiceProvider(ServiceBuilder builder) {
        clientInterface = builder.clientInterface;
        privateKey = builder.privateKey;
        APIHost = builder.APIHost;
        APIVersion = builder.APIVersion;
        APIURL = APIHost + "/v" + APIVersion + "/";
        instance = this;
    }

    public void stopService(){
        working = false;
        nh = null;
    }

    public void startService(){
        nh = new NodeHandler(APIURL);
        if(!nh.test()) {
            working = false;
            U.error("LootSafe Servers are Unreachable!");
            return;
        }
        working = true;
    }


    public String getPrivateKey(){
        return privateKey;
    }
    /**
     * Use to get the built URL
     * @return
     */
    public String getURL(){
        return APIURL;
    }

    public boolean isDebugging(){
        return debug;
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
    public static class ServiceBuilder {
        private Client clientInterface;
        private String privateKey;
        private String APIHost;
        private String APIVersion;

        public ServiceBuilder() {}

        public ServiceBuilder withClient(Client clientInterface){
            this.clientInterface = clientInterface;
            return this;
        }

        public ServiceBuilder withPrivateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public ServiceBuilder withHost(String APIHost) {
            this.APIHost = APIHost;
            return this;
        }

        public ServiceBuilder withVersion(String APIVersion) {
            this.APIVersion = APIVersion;
            return this;
        }

        public ServiceProvider build(){
            return new ServiceProvider(this);
        }
    }






}
