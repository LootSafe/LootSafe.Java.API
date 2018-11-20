package io.lootsafe.api.Data;

public class AssetInfo {
    private String symbol, identifier, name, id;
    private int v;

    public AssetInfo(String symbol, String identifier, String name, String id, int v) {
        this.symbol = symbol;
        this.identifier = identifier;
        this.name = name;
        this.id = id;
        this.v = v;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getV() {
        return v;
    }
}
