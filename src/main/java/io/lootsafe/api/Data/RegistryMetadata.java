package io.lootsafe.api.Data;

public class RegistryMetadata {
    private String registry, provider;
    private int version;

    public RegistryMetadata(String registry, String provider, int version) {
        this.registry = registry;
        this.provider = provider;
        this.version = version;
    }

    public String getRegistry() {
        return registry;
    }

    public String getProvider() {
        return provider;
    }

    public int getVersion() {
        return version;
    }
}
