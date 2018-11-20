package io.lootsafe.api.Requests;

import io.lootsafe.api.Data.AssetData;
import io.lootsafe.api.Data.AssetMetadata;
import io.lootsafe.api.Data.RegistryMetadata;
import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.RequestEntityProcessing;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.*;

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


    private JsonObject genericRequest(String formedNodeString) {
        U.debug(formedNodeString);
        U.debug("Trying to retrieve info from " + apiUrl + formedNodeString);
        try {
            Response response = webTarget
                    .path("/" + formedNodeString)
                    .request(MediaType.APPLICATION_JSON)
                    .header("key", apiKey)
                    .header("otp", otp)
                    .get();
            U.debug("Status:" + response.getStatus());
            if (response.getStatus() != 200) {
                U.debug("There was an error while processing your request!");
                throw new WebApplicationException(response);
            } else {
                JsonObject responseJson = response.readEntity(JsonObject.class);
                U.debug(responseJson.toString());
                responseJson = Json.createObjectBuilder(responseJson).add("error", false).build();
                return responseJson;
            }

        } catch (ProcessingException e) {
            U.debug("Error Contacting LootSafe Servers");
            throw e;
        }
    }

    private JsonObject postRequest(String formedNodeString, JsonObject input) {
        U.debug(formedNodeString);
        U.debug("Trying to POST to " + apiUrl + formedNodeString);
        try {
            Response response = webTarget
                    .path("/" + formedNodeString)
                    .request(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("key", apiKey)
                    .header("otp", otp)
                    .post(Entity.json(input));
            U.debug("Status:" + response.getStatus());
            if (response.getStatus() != 200) {
                U.debug("There was an error while processing your request!");
                throw new WebApplicationException(response);
            } else {
                JsonObject responseJson = response.readEntity(JsonObject.class);
                responseJson = Json.createObjectBuilder(responseJson).add("error", false).build();
                return responseJson;
            }
        } catch (ProcessingException e) {
            U.debug("Error Contacting LootSafe Servers");
            throw e;
        }
    }


    /**
     * Attempts to retrieve a list of all assets on the registry.
     * @return Returns a String List with all the assets or null if the request failed.
     */
    public List<String> getRegAssets(){
        JsonObject response = getRegAssetsRaw();
        if(response.getBoolean("error")) return null;
        JsonArray data = response.getJsonArray("data");
        return Arrays.asList(data.toArray(new String[data.size()]));
    }

    /**
     * Attempts to retrieve a list of all assets on the registry. Can be checked with jsonObject.getBoolean("error").
     * @return returns the raw json of this request- {@value Requests#DOC_URL_Text}
     */
    public JsonObject getRegAssetsRaw(){
        JsonObject response = genericRequest(Requests.GetRegAssets);
        return response != null ? response : jsonError();
    }

    /**
     * Attempt to retrieve the address of the owner of the registry
     * @return Returns a string with the address of the owner of the registry, or null if the request failed.
     */
    public String getRegOwner(){
        JsonObject response = getRegOwnerRaw();
        return response.getBoolean("error") ? null : response.getString("data");
    }

    /**
     * Attempt to retrieve the address of the owner of the registry
     * @return returns the raw json of this request {@value Requests#DOC_URL_Text}
     */
    public JsonObject getRegOwnerRaw(){
        JsonObject response = genericRequest(Requests.GetRegOwner);
        return response != null ? response : jsonError();
    }


    /**
     * Attempt to lookup the address of an asset in the registry.
     * @param asset the string for the asset you are looking up
     * @return Returns a string with the asset's address or null if the request failed.
     */
    public String findAssetAddress(String asset){
        JsonObject response = findAssetAddressRaw(asset);
        return response.getBoolean("error") ? null : response.getString("data");
    }

    /**
     * Attempt to lookup the address of an asset in the registry.
     * @param asset the string for the asset you are looking up
     * @return Returns the raw json of this request {@value Requests#DOC_URL_Text}
     */
    public JsonObject findAssetAddressRaw(String asset){
        JsonObject response = genericRequest(Requests.GetFindAddress.replace("%1", asset));
        return response != null ? response : jsonError();
    }

    /**
     * Attempt to get the owner of an asset
     * @param asset the address of the asset
     * @return Returns the owner of the asset or null if the request failed;
     */
    public String getAssetOwner(String asset){
        JsonObject response = getAssetOwnerRaw(asset);
        return response.getBoolean("error") ? null : response.getString("data");
    }

    /**
     * Attempt to get the owner of an asset.
     * @param asset the address of the asset.
     * @return Returns the raw json of this request {@value Requests#DOC_URL_Text}
     */
    public JsonObject getAssetOwnerRaw(String asset){
        JsonObject response = genericRequest(Requests.GetAssetOwner.replace(("%1"), asset));
        return response != null ? response : jsonError();
    }

    /**
     * Attempts to set metadata on an asset.
     * @param asset The address of the asset
     * @param key the entry name
     * @param value the entry value
     * @param shortname a short name for the field
     * @return Returns the a data string or null if the request failed.
     */
    public String setAssetMetadata(String asset, String key, String value, String shortname){
        JsonObject response = setAssetMetadataRaw(asset, key, value, shortname);
        return response.getBoolean("error") ? null : response.getString("data");
    }

    /**
     * Attempts to set metadata on an asset. Can be checked with jsonObject.getBoolean("error")
     * @param asset The address of the asset
     * @param key the entry name
     * @param value the entry value
     * @param shortname a short name for the field
     * @return Returns the raw Json of this request {@value Requests#DOC_URL_Text}
     */
    public JsonObject setAssetMetadataRaw(String asset, String key, String value, String shortname){

        JsonObject jsonRequest = Json.createObjectBuilder()
                .add("key", key)
                .add("value", value)
                .add("shortname", shortname)
                .build();
        JsonObject response = postRequest(Requests.PostSetMetaData.replace("%1", asset), jsonRequest);
        return response != null ? response : jsonError();
    }

    /**
     * Attempts to get a list of all the assets available in the database.
     * @return Returns a List with all of the assets or null if the request failed.
     */
    public List<String> getListAssets(){
        JsonObject response = getListAssetsRaw();
        if(response.getBoolean("error")) return null;
        JsonArray data = response.getJsonArray("data");
        return Arrays.asList(data.toArray(new String[data.size()]));
    }

    /**
     * Attempts to get a list of all assets available in the database. Can be checked with jsonObject.getBoolean("error")
     * @return Returns the raw Json of this request. {@value Requests#DOC_URL_Text}
     */
    public JsonObject getListAssetsRaw(){
        JsonObject response = genericRequest(Requests.GetListAssets);
        return response != null ? response : jsonError();
    }
    /**
     * Attempts to get all information about a particular asset using it's identifier.
     * Metadata can be seen with assetData.getMetadata().
     * @param assetIdentifier the Asset Identifier -- No spaces
     * @return Returns a AssetData class with all it's information including metadata*/
    public AssetData getAsset(String assetIdentifier){
        JsonObject response = getAssetRaw(assetIdentifier);
        if(response.getBoolean("error")) return null;
        JsonObject data= response.getJsonObject("data");
        JsonObject metaDataJson = data.getJsonObject("metadata");
        AssetMetadata metadata = new AssetMetadata(
                metaDataJson.getString("rarity") ,
                metaDataJson.getString("icon"),
                metaDataJson.getString("test"),
                metaDataJson.getString("testing"));
        return new AssetData(
                data.getString("_id"),
                data.getString("name"),
                data.getString("symbol"),
                data.getString("identifier"),
                data.getString("address"),
                data.getInt("__v"),
                data.getInt("confirmation"),
                data.getInt("supply"),
                metadata);
    }
    /**
     * Attempts to get all information about a particular asset using it's identifier
     * @param assetIdentifier the Asset Identifier -- No spaces
     * @return Returns the raw Json for this request {@value Requests#DOC_URL_Text}
     */
    public JsonObject getAssetRaw(String assetIdentifier){
        JsonObject response = genericRequest(Requests.GetAsset.replace("%1", assetIdentifier));
        return response != null ? response : jsonError();
    }

    /***
     * Attempts to create an asset on the registry and on the block chain.
     * @param symbol Asset's Symbol (Keep it short)
     * @param name A human readable name for your asset.
     * @param identifier A spaceless identifier for the asset in the registry.
     * @return Returns the item's address, or null if it was unable to make one.
     */
    public String createAsset(String symbol, String name, String identifier){
        JsonObject response = createAssetRaw(symbol, name, identifier);
        return response.getBoolean("error") ? null : response.getString("data");
    }

    /***
     * Attempts to create an asset on the registry and on the block chain. Validity can be checked with jsonObject.getBoolean("error");
     * @param symbol Asset's Symbol (Keep it short)
     * @param name A human readable name for your asset.
     * @param identifier A spaceless identifier for the asset in the registry.
     * @return Returns the raw json of the request. Json will include "error" true/false. {@value Requests#DOC_URL_Text}
     */
    public JsonObject createAssetRaw(String symbol, String name, String identifier){
        JsonObject jsonRequest = Json.createObjectBuilder()
                .add("symbol", symbol)
                .add("name",name)
                .add("identifier", identifier)
                .build();

        JsonObject response = postRequest(Requests.PostAsset, jsonRequest);
        return response != null ? response : jsonError();
    }

    /**
     * Attempts to set an IPFS file for an asset
     * @param asset the asset
     * @param file the file
     * @return Returns a data string or null if the request failed.
     */
    public String setMetadataFile(String asset, String file){
        JsonObject response  = setMetadataFileRaw(asset, file);
        return response.getBoolean("error") ? null : response.getString("data");

    }


    /**
     * Attempts to set an IPFS for an asset Can be checked with jsonObject.getBoolean("error")
     * @param asset the asset
     * @param file the file
     * @return Returns the raw JSon for this request {@value Requests#DOC_URL_Text}}
     */
    public JsonObject setMetadataFileRaw(String asset, String file){
        JsonObject jsonRequest = Json.createObjectBuilder().build();
        JsonObject response = postRequest(Requests.PostSetMetadataFile.replace("%1", asset).replace("%2", file), jsonRequest);
        return response != null ? response : jsonError();
    }


    /**
     * Attempts to mint assets into a wallet.
     * @param walletAddress Address of the receiving wallet
     * @param assetID Address of the asset being minted
     * @param quantity the amount of assets being minted
     * @return Returns the transaction id or null if the request failed;
     */
    public String mintAsset(String walletAddress, String assetID, int quantity){
        JsonObject response = mintAssetRaw(walletAddress, assetID, quantity);
        return response.getBoolean("error") ? null : response.getString("data");
    }

    /**
     * Attempts to mint assets into a wallet. Can be checked with jsonObject.getBoolean("error")
     * @param walletAddress Address of the receiving wallet
     * @param assetID Address of the asset being minted
     * @param quantity the amount of assets being minted
     * @return Returns the raw Json of this request {@value Requests#DOC_URL_Text}
     */
    public JsonObject mintAssetRaw(String walletAddress, String assetID, int quantity){
        JsonObject response = genericRequest(Requests.GetMint
                                .replace("%1", walletAddress)
                                .replace("%2", assetID)
                                .replace("%3", quantity + ""));
        return response != null ? response : jsonError();

    }

    /**
     * Attempts to get the registry's metadata as a RegistryMetadata object.
     * @return Returns a RegistryMetadata Object or null if the request failed.
     */
    public RegistryMetadata GetMetadata(){
        JsonObject response = GetMetadataRaw();
        if(response.getBoolean("error")) return null;
        JsonObject data = response.getJsonObject("data");
        return new RegistryMetadata(
                data.getString("registry"),
                data.getString("provider"),
                data.getInt("version"));
    }

    /**
     * Attempts to get the registry's metadata as a RegistryMetadata object.
     * @return Returns the raw Json for this request {@value Requests#DOC_URL_Text};
     */
    public JsonObject GetMetadataRaw(){
        JsonObject response = genericRequest(Requests.GetMetadata);
        return response != null ? response : jsonError();
    }

    private JsonObject jsonError(){
        return Json.createObjectBuilder()
                .add("error", true)
                .build();

    }



    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/
    /******************      Everything Below this block is deprecated and obsolete!       **************/
    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/

    @Deprecated
    public double getBalanceToken(String ethAccount) {
        JsonObject json = genericRequest(Requests.balanceToken + "/" + ethAccount);
        try {
            return Double.parseDouble(json.getString("data"));
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Something is Wrong with LootSafe! We did not receive a number for the balance! \n" + json.toString());
        }
    }

    @Deprecated
    public int getBalanceItem(String itemAddress, String ethAccount) throws WebApplicationException, ProcessingException {
        JsonObject json = genericRequest(Requests.balanceItem + "/" + itemAddress + "/" + ethAccount);
        try {
            return Integer.parseInt(json.getString("data"));
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Something is Wrong with LootSafe! We did not receive a number for the balance! \n" + json.toString());
        }
    }

    @Deprecated
    public Map<String, String> getBalanceItems(String ethAccount) {
        JsonObject json = genericRequest(Requests.balanceItems + "/" + ethAccount);
        Map<String, String> items = new HashMap<String, String>();
        if (json != null) {

            JsonArray jsonItems = json.getJsonArray("data");

            for (int ix = 0; ix < jsonItems.size(); ix++) {
                JsonObject pair = jsonItems.getJsonObject(ix);
                Set<String> keys = pair.keySet();
                for (String key : keys) {
                    items.put(key, pair.getString(key));
                }
            }
        }
        return items;
    }

    @Deprecated
    public Set<String> getCraftables() {
        JsonObject json = genericRequest(Requests.craftables);
        Set<String> craftables = new HashSet<String>();
        if (json != null) {

            JsonArray jsonCraftables = json.getJsonArray("data");

            for (int ix = 0; ix < jsonCraftables.size(); ix++) {
                craftables.add(jsonCraftables.getString(ix));
            }
        }
        return craftables;
    }

    @Deprecated
    public Set<String> getDeconsructables() {
        JsonObject json = genericRequest(Requests.deconstructables);
        Set<String> deconstructables = new HashSet<String>();
        if (json != null) {

            JsonArray jsonDeconstructables = json.getJsonArray("data");

            for (int ix = 0; ix < jsonDeconstructables.size(); ix++) {
                deconstructables.add(jsonDeconstructables.getString(ix));
            }
        }
        return deconstructables;
    }

    @Deprecated
    public Set<String> getRecipe(String itemAddr) {
        JsonObject json = genericRequest(Requests.craftingRecipe + "/" + itemAddr);
        Set<String> recipe = new HashSet<String>();
        if (json != null) {

            JsonArray jsonItems = json.getJsonArray("data");
            U.debug("Recipe:");
            U.debug(jsonItems.toString());
            for (int ix = 0; ix < jsonItems.size(); ix++) {
                JsonArray innerArray = jsonItems.getJsonArray(ix);
                for (int i = 0; i < innerArray.size(); i++) {
                    recipe.add(innerArray.getString(i));
                }
            }

        }
        return recipe;
    }

    @Deprecated
    public Set<String> getDeconstructionRecipe(String itemAddr) {
        JsonObject json = genericRequest(Requests.deconstructionRecipe + "/" + itemAddr);
        Set<String> recipe = new HashSet<String>();
        if (json != null) {

            JsonArray jsonItems = json.getJsonArray("data");
            U.debug("Recipe:");
            U.debug(jsonItems.toString());
            for (int ix = 0; ix < jsonItems.size(); ix++) {
                JsonArray innerArray = jsonItems.getJsonArray(ix);
                for (int i = 0; i < innerArray.size(); i++) {
                    recipe.add(innerArray.getString(i));
                }
            }

        }
        return recipe;
    }

    @Deprecated
    public JsonObject postNewRecipe(JsonObject recipeDetails) {
        JsonObject response = postRequest(Requests.newRecipe, recipeDetails);

        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    @Deprecated
    public JsonObject postRecipeRemoval(String itemAddress) {
        JsonObject recipeDetails = Json.createObjectBuilder()
                .add("item", itemAddress)
                .build();
        JsonObject response = postRequest(Requests.removeRecipe, recipeDetails);
        if (response != null) return response;

        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();

    }

    @Deprecated
    public JsonObject getEvents() {
        JsonObject response = genericRequest(Requests.events);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    @Deprecated
    public JsonObject getMeta() {
        return genericRequest(Requests.meta);
    }

    @Deprecated
    public String getTokenAddress() {
        JsonObject json = genericRequest(Requests.tokenAddress);
        return json != null ? json.getString("address") : "";
    }

    @Deprecated
    public JsonObject postNewItem(String name, String id, String totalSupply) {
        JsonObject newItemJson = Json.createObjectBuilder()
                .add("name", name)
                .add("id", id)
                .add("totalSupply", totalSupply)
                .build();
        JsonObject response = postRequest(Requests.newItem, newItemJson);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();

    }

    @Deprecated
    public JsonObject postSpawnItem(String itemAddress, String toEthAddress) {
        JsonObject spawnItemJson = Json.createObjectBuilder()
                .add("itemAddress", itemAddress)
                .add("to", toEthAddress)
                .build();
        JsonObject response = postRequest(Requests.spawnItem, spawnItemJson);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    @Deprecated
    public JsonObject postClearAvailibilty(String itemAddress) {
        JsonObject clearAvailabilityJson = Json.createObjectBuilder()
                .add("itemAddress", itemAddress)
                .build();

        JsonObject response = postRequest(Requests.clearAvailability, clearAvailabilityJson);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    @Deprecated
    public Set<String> getItemsList() {
        JsonObject response = genericRequest(Requests.items);
        JsonArray itemsArray = response.getJsonArray("data");
        Set<String> items = new HashSet<String>();
        if (response != null) {
            for (int ix = 0; ix < itemsArray.size(); ix++) {
                items.add(itemsArray.getString(ix));
            }
        }
        return items;
    }

    @Deprecated
    public JsonObject getItem(String itemName) {
        JsonObject response = genericRequest(Requests.item + "/" + itemName);
        return response;
    }

    @Deprecated
    public JsonObject getItemByAddress(String itemAddress) {
        JsonObject response = genericRequest(Requests.itemByAddress + "/" + itemAddress);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    @Deprecated
    public JsonObject getLedger() {
        JsonObject response = genericRequest(Requests.ledger);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    @Deprecated
    public Set<String> getItemAddresses() {
        JsonObject response = genericRequest(Requests.itemAddresses);
        JsonArray itemsArray = response.getJsonObject("data").getJsonArray("items");
        Set<String> items = new HashSet<String>();
        if (response != null) {
            for (int ix = 0; ix < itemsArray.size(); ix++) {
                items.add(itemsArray.getString(ix));
            }
        }
        return items;
    }

    @Deprecated
    public Set<String> getLootboxChances() {
        JsonObject response = genericRequest(Requests.chances);
        JsonArray chanceArray = response.getJsonArray("data");
        Set<String> chances = new HashSet<String>();
        if (response != null) {
            for (int ix = 0; ix < chanceArray.size(); ix++) {
                chances.add(chanceArray.getString(ix));
            }
        }
        return chances;
    }

    @Deprecated
    public Set<String> getLootboxItems(String rarity) {
        JsonObject response = genericRequest(Requests.lootboxItems + "/" + rarity);
        JsonArray itemsArray = response.getJsonArray("data");
        Set<String> items = new HashSet<String>();
        if (response != null) {
            for (int ix = 0; ix < itemsArray.size(); ix++) {
                items.add(itemsArray.getString(ix));
            }
        }
        return items;
    }

    @Deprecated
    public double getCost() {
        JsonObject response = genericRequest(Requests.lootboxCost);
        try {
            return Double.parseDouble(response.getString("data"));
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Something is Wrong with LootSafe! We did not receive a number for the balance! \n" + response.toString());
        }
    }

    @Deprecated
    public JsonObject postLootboxCostUpdate(String newCost) {
        JsonObject response = genericRequest(Requests.lootboxCostUpdate + "/" + newCost);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();

    }

    @Deprecated
    public JsonObject postLootboxChanceUpdate(String newChances) {
        JsonObject response = genericRequest(Requests.lootboxChanceUpdate + "/" + newChances);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    @Deprecated
    public JsonObject postLootboxAddItem(String itemAddress, String rarity) {
        JsonObject jsonRequest = Json.createObjectBuilder()
                .add("item", itemAddress)
                .add("rarity", rarity)
                .build();
        JsonObject response = postRequest(Requests.lootboxAdd, jsonRequest);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }




}
