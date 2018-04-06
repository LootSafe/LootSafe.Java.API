package io.lootsafe.api.Requests;

import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.*;

/**
 * Created by Adam Sanchez on 4/5/2018.
 */
public class Requests {

    private static final String balanceToken = "balance/token";
    private static final String balanceItem = "balance/item";
    private static final String balanceItems = "balance/items";

    private static final String craftables = "craftables";
    private static final String deconstructables = "deconstructables";
    private static final String craftingRecipe = "recipe/get";
    private static final String deconstructionRecipe = "recipe/deconstruction/get";

    private static final String newRecipe = "recipe/new";
    private static final String removeRecipe = "recipe/remove";

    private static final String events = "events";

    private static final String meta = "";
    private static final String newItem = "item/new";
    private static final String spawnItem = "item/spawn";
    private static final String clearAvailability = "item/clearAvailability";
    private static final String tokenAddress = "address/token";

    private static final String items = "item/list";
    private static final String item = "item/get";
    private static final String itemByAddress = "item/get/address";
    private static final String itemAddresses = "item/addresses/get";
    private static final String ledger = "item/ledger";

    private static final String chances = "lootbox/chances";
    private static final String lootboxItems = "lootbox/items";
    private static final String lootboxCost = "lootbox/cost";
    private static final String lootboxAdd = "lootbox/item/add";
    private static final String lootboxCostUpdate = "lootbox/cost";
    private static final String lootboxChanceUpdate = "lootbox/chances/update";


    /****************************************************************************************************/
    /*************************************BALANCES*******************************************************/
    /****************************************************************************************************/

    public static String getBalanceToken(String ethAccount) {
        JsonObject json = getInstance().genericRequest(balanceToken + "/" + ethAccount);
        return json != null ? json.getString("data") : "";

    }

    public static String getBalanceItem(String itemAddress, String ethAccount) {
        JsonObject json = getInstance().genericRequest(balanceItem + "/" + itemAddress + "/" + ethAccount);
        return json != null ? json.getString("data") : "";
    }


    public static Map<String, String> getBalanceItems(String ethAccount) {
        JsonObject json = getInstance().genericRequest(balanceItems + "/" + ethAccount);
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
    /****************************************************************************************************/
    /*************************************CRAFTING*******************************************************/
    /****************************************************************************************************/

    public static Set<String> getCraftables() {
        JsonObject json = getInstance().genericRequest(craftables);
        Set<String> craftables = new HashSet<String>();
        if (json != null) {

            JsonArray jsonCraftables = json.getJsonArray("data");

            for (int ix = 0; ix < jsonCraftables.size(); ix++) {
                craftables.add(jsonCraftables.getString(ix));
            }
        }
        return craftables;
    }

    public static Set<String> getDeconsructables() {
        JsonObject json = getInstance().genericRequest(deconstructables);
        Set<String> deconstructables = new HashSet<String>();
        if (json != null) {

            JsonArray jsonDeconstructables = json.getJsonArray("data");

            for (int ix = 0; ix < jsonDeconstructables.size(); ix++) {
                deconstructables.add(jsonDeconstructables.getString(ix));
            }
        }
        return deconstructables;
    }

    public static Set<String> getRecipe(String itemAddr) {
        JsonObject json = getInstance().genericRequest(craftingRecipe + "/" + itemAddr);
        Set<String> recipe = new HashSet<String>();
        if (json != null) {

            JsonArray jsonItems = json.getJsonArray("data");
            U.debug("Recipe:");
            U.debug(jsonItems.toString());
            for (int ix = 0; ix < jsonItems.size(); ix++) {
                JsonArray innerArray = jsonItems.getJsonArray(ix);
                for (int i = 0; ix < innerArray.size(); i++) {
                    recipe.add(innerArray.getString(i));
                }
            }

        }
        return recipe;
    }

    public static Set<String> getDeconstructionRecipe(String itemAddr) {
        JsonObject json = getInstance().genericRequest(deconstructionRecipe + "/" + itemAddr);
        Set<String> recipe = new HashSet<String>();
        if (json != null) {

            JsonArray jsonItems = json.getJsonArray("data");
            U.debug("Recipe:");
            U.debug(jsonItems.toString());
            for (int ix = 0; ix < jsonItems.size(); ix++) {
                JsonArray innerArray = jsonItems.getJsonArray(ix);
                for (int i = 0; ix < innerArray.size(); i++) {
                    recipe.add(innerArray.getString(i));
                }
            }

        }
        return recipe;
    }

    public static JsonObject postNewRecipe(JsonObject recipeDetails) {
        JsonObject response = getInstance().postRequest(newRecipe, recipeDetails);

        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    public static JsonObject postRecipeRemoval(String itemAddress) {
        JsonObject recipeDetails = Json.createObjectBuilder()
                .add("item", itemAddress)
                .build();
        JsonObject response = getInstance().postRequest(removeRecipe, recipeDetails);
        if (response != null) return response;

        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();

    }

    /****************************************************************************************************/
    /****************************************EVENTS******************************************************/
    /****************************************************************************************************/


    public static JsonObject getEvents() {
        JsonObject response = getInstance().genericRequest(events);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    /****************************************************************************************************/
    /****************************************General******************************************************/
    /****************************************************************************************************/

    public static JsonObject getMeta() {
        return getInstance().genericRequest(meta);
    }

    public static String getTokenAddress() {
        JsonObject json = getInstance().genericRequest(tokenAddress);
        return json != null ? json.getString("address") : "";
    }

    public static JsonObject postNewItem(String name, String id, String totalSupply) {
        JsonObject newItemJson = Json.createObjectBuilder()
                .add("name", name)
                .add("id", id)
                .add("totalSupply", totalSupply)
                .build();
        JsonObject response = getInstance().postRequest(newItem, newItemJson);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();

    }

    public static JsonObject postSpawnItem(String itemAddress, String toEthAddress) {
        JsonObject spawnItemJson = Json.createObjectBuilder()
                .add("itemAddress", itemAddress)
                .add("to", toEthAddress)
                .build();
        JsonObject response = getInstance().postRequest(spawnItem, spawnItemJson);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    public static JsonObject postClearAvailibilty(String itemAddress) {
        JsonObject clearAvailabilityJson = Json.createObjectBuilder()
                .add("itemAddress", itemAddress)
                .build();

        JsonObject response = getInstance().postRequest(clearAvailability, clearAvailabilityJson);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    /****************************************************************************************************/
    /**(**************************************Items******************************************************/
    /****************************************************************************************************/

    public static Set<String> getItemsList() {
        JsonObject response = getInstance().genericRequest(items);
        JsonArray itemsArray = response.getJsonArray("data");
        Set<String> items = new HashSet<String>();
        if (response != null) {
            for (int ix = 0; ix < itemsArray.size(); ix++) {
                items.add(itemsArray.getString(ix));
            }
        }
        return items;
    }

    public static String getItem(String itemName){
        JsonObject response = getInstance().genericRequest(item + "/" + itemName);
        if(response != null) return response.getString("itemResponse");
        return "";
    }

    public static JsonObject getItemByAddress(String itemAddress){
        JsonObject response = getInstance().genericRequest(itemByAddress + "/" + itemAddress);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    public static JsonObject getLedger(){
        JsonObject response = getInstance().genericRequest(ledger);
        if(response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    public static Set<String> getItemAddresses(){
        JsonObject response = getInstance().genericRequest(itemAddresses);
        JsonArray itemsArray = response.getJsonObject("data").getJsonArray("items");
        Set<String> items = new HashSet<String>();
        if (response != null) {
            for (int ix = 0; ix < itemsArray.size(); ix++) {
                items.add(itemsArray.getString(ix));
            }
        }
        return items;
    }


    /****************************************************************************************************/
    /**(**************************************LootBox***************************************************/
    /****************************************************************************************************/

    public static Set<String> getLootboxChances(){
            JsonObject response = getInstance().genericRequest(chances);
            JsonArray chanceArray = response.getJsonArray("data");
            Set<String> chances = new HashSet<String>();
            if (response != null) {
                for (int ix = 0; ix < chanceArray.size(); ix++) {
                    chances.add(chanceArray.getString(ix));
                }
            }
            return chances;
    }

    public static Set<String> getLootboxItems(String rarity){
        JsonObject response = getInstance().genericRequest(lootboxItems + "/" + rarity);
        JsonArray itemsArray = response.getJsonArray("data");
        Set<String> items = new HashSet<String>();
        if (response != null) {
            for (int ix = 0; ix < itemsArray.size(); ix++) {
                items.add(itemsArray.getString(ix));
            }
        }
        return items;
    }

    public static String getCost(){
        JsonObject response = getInstance().genericRequest(lootboxCost);
        if(response != null) return response.getString("data");
        return "";
    }

    public static JsonObject postLootboxCostUpdate(String newCost){
        JsonObject response = getInstance().genericRequest(lootboxCostUpdate + "/" + newCost);
        if(response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();

    }

    public static JsonObject postLootboxChanceUpdate(String newChances) {
        JsonObject response = getInstance().genericRequest(lootboxChanceUpdate + "/" + newChances);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    public static JsonObject postLootboxAddItem(String itemAddress, String rarity){
        JsonObject jsonRequest = Json.createObjectBuilder()
                .add("item", itemAddress)
                .add("rarity", rarity)
                .build();
        JsonObject response = getInstance().postRequest(lootboxAdd , jsonRequest);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }








        /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/


    private static NodeHandler getInstance() {
        return ServiceProvider.getInstance().getNodeHandler();
    }


}
