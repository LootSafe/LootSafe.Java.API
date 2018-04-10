package io.lootsafe.api.Requests;

import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.Assert.*;

/**
 * Created by Adam Sanchez on 4/9/2018.
 */
public class RequestsTest {
    static String testEthAccount = "0x6e029820707c41f9ce04070930e3d650db769ed6";
    static String testItem = "0x33e7e1ee81dcb63f28f264beedce6f6941bde451";
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
            .withHost("http://localhost:1337")
            .withPrivateKey("pWpzWuxoKUKAmlHc0wPi7lFS38FTth")
            .withOTP("ccccccglcrcdfbvrelclvcgjbbkkjtkhcvdbnlljlgke")
            .withVersion("1")
            .build();

    @Test
    public void getBalanceToken() throws Exception {
        sv.startService();
        String response = Requests.getBalanceToken(testEthAccount);
        double responseNumber = Double.parseDouble(response);
        assertNotNull(response);
        assertTrue(responseNumber <= 0 || responseNumber > 0);
    }

    @Test
    public void getBalanceItem() throws Exception {
    }

    @Test
    public void getBalanceItems() throws Exception {
    }

    @Test
    public void getCraftables() throws Exception {
    }

    @Test
    public void getDeconsructables() throws Exception {
    }

    @Test
    public void getRecipe() throws Exception {
    }

    @Test
    public void getDeconstructionRecipe() throws Exception {
    }

    @Test
    public void postNewRecipe() throws Exception {
    }

    @Test
    public void postRecipeRemoval() throws Exception {
    }

    @Test
    public void getEvents() throws Exception {
    }

    @Test
    public void getMeta() throws Exception {
    }

    @Test
    public void getTokenAddress() throws Exception {
    }

    @Test
    public void postNewItem() throws Exception {
    }

    @Test
    public void postSpawnItem() throws Exception {
    }

    @Test
    public void postClearAvailibilty() throws Exception {
    }

    @Test
    public void getItemsList() throws Exception {
    }

    @Test
    public void getItem() throws Exception {
    }

    @Test
    public void getItemByAddress() throws Exception {
    }

    @Test
    public void getLedger() throws Exception {
    }

    @Test
    public void getItemAddresses() throws Exception {
    }

    @Test
    public void getLootboxChances() throws Exception {
    }

    @Test
    public void getLootboxItems() throws Exception {
    }

    @Test
    public void getCost() throws Exception {
    }

    @Test
    public void postLootboxCostUpdate() throws Exception {
    }

    @Test
    public void postLootboxChanceUpdate() throws Exception {
    }

    @Test
    public void postLootboxAddItem() throws Exception {
    }

}