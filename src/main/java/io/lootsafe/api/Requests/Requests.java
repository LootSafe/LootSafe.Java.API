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

            for(int ix = 0; ix < jsonItems.size(); ix ++){
                JsonObject pair = jsonItems.getJsonObject(ix);
                Set<String> keys = pair.keySet();
                for(String key: keys){
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

            for(int ix = 0; ix < jsonCraftables.size(); ix ++){
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

            for(int ix = 0; ix < jsonDeconstructables.size(); ix ++){
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
            for(int ix = 0; ix < jsonItems.size(); ix ++){
                JsonArray innerArray = jsonItems.getJsonArray(ix);
                for(int i = 0; ix < innerArray.size(); i ++ ){
                    recipe.add(innerArray.getString(i));
                }
            }

        }
        return recipe;
    }

    public static String postNewRecipe(){

    }

    public static String postRecipeRemoval(){

    }


    public static Set<String> getDeconstructionRecipe(String itemAddr) {
        JsonObject json = getInstance().genericRequest(deconstructionRecipe + "/" + itemAddr);
        Set<String> recipe = new HashSet<String>();
        if (json != null) {

            JsonArray jsonItems = json.getJsonArray("data");
            U.debug("Recipe:");
            U.debug(jsonItems.toString());
            for(int ix = 0; ix < jsonItems.size(); ix ++){
                JsonArray innerArray = jsonItems.getJsonArray(ix);
                for(int i = 0; ix < innerArray.size(); i ++ ){
                    recipe.add(innerArray.getString(i));
                }
            }

        }
        return recipe;
    }







    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/


    private static NodeHandler getInstance() {
        return ServiceProvider.getInstance().getNodeHandler();
    }


}
