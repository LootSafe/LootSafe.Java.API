package io.lootsafe.api.Data;

public class AssetData {

    private String id, name, symbol, identifier, address;
    private int v, confirmation,supply;
    private AssetMetadata metadata;

    public AssetData(String id, String name, String symbol, String identifier, String address, int v, int confirmation, int supply, AssetMetadata metadata) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.identifier = identifier;
        this.address = address;
        this.v = v;
        this.confirmation = confirmation;
        this.supply = supply;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getAddress() {
        return address;
    }

    public int getV() {
        return v;
    }

    public int getConfirmation() {
        return confirmation;
    }

    public int getSupply() {
        return supply;
    }

    public AssetMetadata getMetadata() {
        return metadata;
    }
}
