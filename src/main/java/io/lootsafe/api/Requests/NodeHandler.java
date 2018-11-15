package io.lootsafe.api.Requests;

import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */

public class NodeHandler {
    //TODO Add in new End points, Add Raw Versions of End points, and Modify current end points to deprecated along with docs and Invalidation
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
                    .request(MediaType.APPLICATION_JSON)
                    .header("key", apiKey)
                    .header("otp", otp)
                    .post(Entity.json(input));
            U.debug("Status:" + response.getStatus());
            if (response.getStatus() != 200) {
                U.debug("There was an error while processing your request!");
                throw new WebApplicationException(response);
            } else {
                JsonObject responseJson = response.readEntity(JsonObject.class);
                return responseJson;
            }
        } catch (ProcessingException e) {
            U.debug("Error Contacting LootSafe Servers");
            throw e;
        }
    }



    /****************************************************************************************************/
    /*************************************BALANCES*******************************************************/
    /****************************************************************************************************/

    public double getBalanceToken(String ethAccount) {
        JsonObject json = genericRequest(Requests.balanceToken + "/" + ethAccount);
        try {
            return Double.parseDouble(json.getString("data"));
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Something is Wrong with LootSafe! We did not receive a number for the balance! \n" + json.toString());
        }
    }

    public int getBalanceItem(String itemAddress, String ethAccount) throws WebApplicationException, ProcessingException {
        JsonObject json = genericRequest(Requests.balanceItem + "/" + itemAddress + "/" + ethAccount);
        try {
            return Integer.parseInt(json.getString("data"));
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Something is Wrong with LootSafe! We did not receive a number for the balance! \n" + json.toString());
        }
    }


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
    /****************************************************************************************************/
    /*************************************CRAFTING*******************************************************/
    /****************************************************************************************************/

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

    public JsonObject postNewRecipe(JsonObject recipeDetails) {
        JsonObject response = postRequest(Requests.newRecipe, recipeDetails);

        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

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

    /****************************************************************************************************/
    /****************************************EVENTS******************************************************/
    /****************************************************************************************************/


    public JsonObject getEvents() {
        JsonObject response = genericRequest(Requests.events);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    /****************************************************************************************************/
    /****************************************General******************************************************/
    /****************************************************************************************************/

    public JsonObject getMeta() {
        return genericRequest(Requests.meta);
    }

    public String getTokenAddress() {
        JsonObject json = genericRequest(Requests.tokenAddress);
        return json != null ? json.getString("address") : "";
    }

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

    /****************************************************************************************************/
    /**(**************************************Items******************************************************/
    /****************************************************************************************************/

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

    public JsonObject getItem(String itemName) {
        JsonObject response = genericRequest(Requests.item + "/" + itemName);
        return response;
    }

    public JsonObject getItemByAddress(String itemAddress) {
        JsonObject response = genericRequest(Requests.itemByAddress + "/" + itemAddress);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

    public JsonObject getLedger() {
        JsonObject response = genericRequest(Requests.ledger);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

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

    /****************************************************************************************************/
    /**(**************************************LootBox***************************************************/
    /****************************************************************************************************/

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

    public double getCost() {
        JsonObject response = genericRequest(Requests.lootboxCost);
        try {
            return Double.parseDouble(response.getString("data"));
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Something is Wrong with LootSafe! We did not receive a number for the balance! \n" + response.toString());
        }
    }

    public JsonObject postLootboxCostUpdate(String newCost) {
        JsonObject response = genericRequest(Requests.lootboxCostUpdate + "/" + newCost);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();

    }

    public JsonObject postLootboxChanceUpdate(String newChances) {
        JsonObject response = genericRequest(Requests.lootboxChanceUpdate + "/" + newChances);
        if (response != null) return response;
        return Json.createObjectBuilder()
                .add("Error", "Error")
                .build();
    }

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

    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/
    







    public boolean test() {
        return true;
    }

}
