import io.lootsafe.api.Client;
import io.lootsafe.api.Requests.NodeHandler;
import io.lootsafe.api.Requests.Requests;
import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */

public class TestRunner {



    public static void main(String [] args){

        String testEthAccount = "0x6e029820707c41f9ce04070930e3d650db769ed6";
        String testItem = "0x064ffa1ec3ad633dc098e4c2007f6689708ae23b";
        String rarity = "uncommon";
        JsonObject testRecipe = Json.createObjectBuilder()
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

        ServiceProvider sv = new ServiceProvider.ServiceBuilder()
                .withHost("http://localhost:1337")
                .withPrivateKey("pWpzWuxoKUKAmlHc0wPi7lFS38FTth")
                .withOTP("ccccccglcrcdfbvrelclvcgjbbkkjtkhcvdbnlljlgke")
                .withVersion("1")
                .build();

        sv.startService();


        U.debug("-------------------------------------------------------------------");
        U.debug("---------------------------Balances--------------------------------");
        U.debug("-------------------------------------------------------------------");

        //U.info(sv.getNodeHandler().genericRequest("").toString());
        try {
            U.info(Requests.getBalanceToken(testEthAccount));
        } catch (Exception e) {
            U.error("Error Connecting to LootSafe Servers", e);
        }
        U.info(Requests.getBalanceItem(testItem, testEthAccount));
        U.debugMap(Requests.getBalanceItems(testEthAccount));

        U.debug("-------------------------------------------------------------------");
        U.debug("---------------------------Craftables------------------------------");
        U.debug("-------------------------------------------------------------------");

        U.debugSet(Requests.getCraftables());
        U.debugSet(Requests.getDeconsructables());
        U.debugSet(Requests.getRecipe(testItem));
        U.debugSet(Requests.getDeconstructionRecipe(testItem));


        U.debug(Requests.postNewRecipe(testRecipe).toString());
        U.debug(Requests.postRecipeRemoval(testItem).toString());


        U.debug("-------------------------------------------------------------------");
        U.debug("---------------------------Events----------------------------------");
        U.debug("-------------------------------------------------------------------");

        U.debug(Requests.getEvents().toString());

        U.debug("-------------------------------------------------------------------");
        U.debug("---------------------------General---------------------------------");
        U.debug("-------------------------------------------------------------------");

        U.debug(Requests.getMeta().toString());
        U.debug(Requests.getTokenAddress());
        U.debug(Requests.postNewItem("Banana", "0x98734987394870937u9980879870", "100").toString());
        U.debug(Requests.postSpawnItem(testItem, testEthAccount).toString());
        U.debug(Requests.postClearAvailibilty(testItem).toString());

        U.debug("-------------------------------------------------------------------");
        U.debug("--------------------------Items------------------------------------");
        U.debug("-------------------------------------------------------------------");

        U.debugSet(Requests.getItemsList());
        U.debugSet(Requests.getItemAddresses());
        U.debug(Requests.getItem("banana"));
        U.debug(Requests.getItemByAddress(testItem).toString());
        U.debug(Requests.getLedger().toString());

        U.debug("-------------------------------------------------------------------");
        U.debug("-------------------------LootBox-----------------------------------");
        U.debug("-------------------------------------------------------------------");

        U.debug(Requests.postLootboxAddItem(testItem, rarity).toString());
        U.debugSet(Requests.getLootboxItems(rarity));
        U.debug(Requests.getCost());
        U.debug(Requests.postLootboxChanceUpdate("50/30/78").toString());
        U.debug(Requests.postLootboxCostUpdate("343").toString());

        U.debugSet(Requests.getLootboxChances());



        sv.stopService();
    }

}
