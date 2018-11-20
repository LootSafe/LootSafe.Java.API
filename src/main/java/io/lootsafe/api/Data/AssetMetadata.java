package io.lootsafe.api.Data;

public class AssetMetadata {
    private String rarity;
    private String icon;
    private String test;
    private String testing;

    public AssetMetadata(String rarity, String icon, String test, String testing) {
        this.rarity = rarity;
        this.icon = icon;
        this.test = test;
        this.testing = testing;
    }

    public String getRarity() {
        return rarity;
    }

    public String getIcon() {
        return icon;
    }

    public String getTest() {
        return test;
    }

    public String getTesting() {
        return testing;
    }




}
