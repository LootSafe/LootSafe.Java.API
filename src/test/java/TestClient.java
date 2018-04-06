import io.lootsafe.api.Client;
import io.lootsafe.api.Requests.NodeHandler;
import io.lootsafe.api.Requests.Requests;
import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;
import sun.misc.Request;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */
public class TestClient implements Client{


    public static void main(String [] args){
        String testEthAccount = "0x6e029820707c41f9ce04070930e3d650db769ed6";
        String testItem = "0x33e7e1ee81dcb63f28f264beedce6f6941bde451";
        String rarity = "uncommon";

        ServiceProvider sv = new ServiceProvider.ServiceBuilder()
                .withHost("http://localhost:1337")
                .withPrivateKey("dkjsdflkjdfnsf")
                .withOTP("rsa39uwoirjsfhjldkkufjwob4wjwoinflsdjfuh3dhajdiusfoksdfksdjkfjsdkfjsdlkfsdkfkjsdnf3efpf90iosdjlkmsdkfmsnmcajboejsdfm")
                .withVersion("1")
                .build();
        sv.startService();


        U.debug("-------------------------------------------------------------------");
        U.debug("---------------------------Balances--------------------------------");
        U.debug("-------------------------------------------------------------------");

        U.info(sv.getNodeHandler().genericRequest("").toString());
        U.info(Requests.getBalanceToken(testEthAccount));
        U.info(Requests.getBalanceItem(testItem, testEthAccount));
        U.debugMap(Requests.getBalanceItems(testEthAccount));

        U.debug("-------------------------------------------------------------------");
        U.debug("---------------------------Craftables------------------------------");
        U.debug("-------------------------------------------------------------------");

        U.debugSet(Requests.getCraftables());
        U.debugSet(Requests.getDeconsructables());
        U.debugSet(Requests.getRecipe(testItem));
        U.debugSet(Requests.getDeconstructionRecipe(testItem));

        //U.debug(Requests.postNewRecipe(Json.createObjectBuilder().build()).toString());
        U.debug(Requests.postRecipeRemoval("0x003893089348389349scdf0389").toString());


        sv.stopService();
    }

    @Override
    public void notifyPlayer(String playerID) {

    }

    @Override
    public void notifySuccess(String transactionID) {

    }

    @Override
    public void notifyFailure(String transactionID) {

    }
}
