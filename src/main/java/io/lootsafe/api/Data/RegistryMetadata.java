package io.lootsafe.api.Data;

public class RegistryMetadata {
    private String registry, provider;
    private int version;

    public RegistryMetadata(String registry, String provider, int version) {
        this.registry = registry;
        this.provider = provider;
        this.version = version;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
