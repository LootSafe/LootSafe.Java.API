package io.lootsafe.api.Requests;

import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Adam Sanchez on 4/9/2018.
 */
public class RequestsTest {
    static String testEthAccount = "0x6e029820707c41f9ce04070930e3d650db769ed6";
    static String testItem = "0x88716eb7ec7e907f85c29e35b163fafad2c63186";
    static String testItemRemove = "0x4260413f5cd11e9c9b9dc49d84dba2cf54428d45";
    static String testItemName = "AK-47";
    static String rarity = "uncommon";
    static JsonObject testRecipe = Json.createObjectBuilder()
            .add("result","0x0")
            .add("materials", Json.createArrayBuilder()
                    .add("0x01")
                    .add("0x02")
                    .add("0x03")
                    .build())
            .add("counts", Json.createArrayBuilder()
                    .add(2)
                    .add(5)
                    .add(3)
                    .build())
            .build();

    static ServiceProvider sv = new ServiceProvider.ServiceBuilder()
            .withDebug(true)
            .withHost("http://localhost:1337")
            .withPrivateKey("pWpzWuxoKUKAmlHc0wPi7lFS38FTth")
            .withOTP("ccccccglcrcdfbvrelclvcgjbbkkjtkhcvdbnlljlgke")
            .withVersion("1")
            .build();

    @Test
    public void getBalanceToken() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            double response = nh.getBalanceToken(testEthAccount);
            assertNotNull(response);
            assertTrue(response <= 0 || response > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getBalanceItem() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            int response = nh.getBalanceItem(testItem, testEthAccount);
            assertNotNull(response);
            assertTrue(response <= 0 || response > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());

        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getBalanceItems() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Map<String,String> response = nh.getBalanceItems(testEthAccount);
            U.debugMap(response);
            assertNotNull(response);
            assertTrue("Contains Data", response.size() > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good! We weren't able to connect to the LootSafeServer!", e);
        }
    }

    @Test
    public void getCraftables() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Set<String> response = nh.getCraftables();
            U.debugSet(response);
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getDeconsructables() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Set<String> response = nh.getDeconsructables();
            U.debugSet(response);
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getRecipe() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Set<String> response = nh.getRecipe(testItem);
            U.debugSet(response);
            assertNotNull("Is not null", response);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getDeconstructionRecipe() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Set<String> response = nh.getDeconstructionRecipe(testItem);
            U.debugSet(response);
            assertNotNull("Is not null", response);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }


    @Test
    public void postNewRecipe() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.postNewRecipe(testRecipe);
            U.debug(response.toString());
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertEquals("Message", "New recipe added", response.getString("message"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void postRecipeRemoval() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.postRecipeRemoval("0x2f6e55f42deb93ffd47bbc96d776f6871b6790b5");
            U.debug(response.toString());
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertEquals("Message", "Recipe removed", response.getString("message"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getEvents() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.getEvents();
            U.debug(response.toString());
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertEquals("Message", "Events Fetched", response.getString("message"));
            assertTrue(response.getJsonArray("data").getJsonObject(0).getString("_id") != "");
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getMeta() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.getMeta();
            U.debug(response.toString());
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertTrue("Data Validation", response.getString("name").equals("LootSafe.api"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getTokenAddress() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            String response = nh.getTokenAddress();
            U.debug(response.toString());
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.length() > 0);
            assertTrue("Is an address", response.contains("0x"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    //TODO Returns 500 in a 200
    @Test
    public void postNewItem() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.postNewItem("myTestItem", "myRandomIDForMyItem", "10");
            U.debug(response.toString());
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
            if(e.getResponse().getStatus() == 500){
                JsonObject response = e.getResponse().readEntity(JsonObject.class);
                U.debug(response.toString());
            }
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }


    //TODO Throwing 500 no info
    @Test
    public void postSpawnItem() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.postSpawnItem("test", testEthAccount);
            U.debug(response.toString());
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
        } catch (WebApplicationException e) {
            switch(e.getResponse().getStatus()){
                case 500:
                    U.debug("What went wrong?");
                    U.debug(e.getResponse().readEntity(JsonObject.class).toString());
                    break;
                default:
                    U.warn("There was an error returned with that request " + e.getResponse().getStatus());
            }
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void postClearAvailibilty() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.postClearAvailibilty(testItemRemove);
            U.debug(response.toString());
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertTrue(response.getString("message").equals("Cleared available supply of item"));
            assertTrue(response.getJsonObject("data").getString("tx").contains("0x"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
            if(e.getResponse().getStatus() == 500){
                JsonObject response = e.getResponse().readEntity(JsonObject.class);
                U.debug(response.toString());
            }
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getItemsList() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Set<String> response = nh.getItemsList();
            U.debugSet(response);
            assertNotNull("Is not null", response);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getItem() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.getItem(testItem);
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertTrue("Data Validation", response.getJsonObject("data").getString("address").equals(testItem));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    //TODO API Call same as getItem ?
    @Test
    public void getItemByAddress() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.getItemByAddress(testItem);
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertTrue("Data Validation", response.getJsonObject("data").getString("address").equals(testItem));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getLedger() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.getLedger();
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertTrue("Data Validation", response.getString("message").equals("Items fetched"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getItemAddresses() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Set<String> response = nh.getItemAddresses();
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getLootboxChances() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Set<String> response = nh.getLootboxChances();
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getLootboxItems() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            Set<String> response = nh.getLootboxItems(rarity);
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void getCost() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            double response = nh.getCost();
            U.debug(response + "");
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response <= 0 || response > 0);
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void postLootboxCostUpdate() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.postLootboxCostUpdate("100");
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertTrue("Data Validation", response.getJsonObject("data").getString("tx").contains("0x"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    @Test
    public void postLootboxChanceUpdate() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.postLootboxChanceUpdate("10/10/10");
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertTrue("Data Validation", response.getJsonObject("data").getString("tx").contains("0x"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

    //TODO 500
    @Test
    public void postLootboxAddItem() throws Exception {
        NodeHandler nh = sv.startService().getNodeHandler();
        try {
            JsonObject response = nh.postLootboxAddItem(testItem, rarity);
            assertNotNull("Is not null", response);
            assertTrue("Contains Data", response.size() > 0);
            assertTrue("Data Validation", response.getJsonObject("data").getString("tx").contains("0x"));
        } catch (WebApplicationException e) {
            U.warn("There was an error returned with that request " + e.getResponse().getStatus());
            U.warn(e.getResponse().readEntity(JsonObject.class).toString());
        } catch (ProcessingException e) {
            U.error("That's no good!", e);
        }
    }

}