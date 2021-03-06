package io.lootsafe.api.Requests;

import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;

public class CoreTests {

    static String testEthAccount = "0x6e029820707c41f9ce04070930e3d650db769ed6";
    static String testItem = "0x88716eb7ec7e907f85c29e35b163fafad2c63186";
    static String testItemRemove = "0x4260413f5cd11e9c9b9dc49d84dba2cf54428d45";
    static String testItemName = "AK-47";
    static String rarity = "uncommon";

    static ServiceProvider sv = new ServiceProvider.ServiceBuilder()
            .withDebug(true)
            .withHost("http://8bit.diamonds:1337")
            .withPrivateKey("changememeow")
            .withVersion("1")
            .build();


    public void getMetadata(){
        NodeHandler nh = sv.startService().getNodeHandler();
        U.info(nh.getMetadataRaw().toString());
        U.info("Provider: " + nh.getMetadata().getProvider());
    }

    @Test
    public void mintAsset(){
        NodeHandler nh = sv.startService().getNodeHandler();
        U.info(nh.mintAssetRaw("0xDd83c299B59BbAB987398154042aaF07dE6DC462", "0xF8C808C720561bEbA2d1d5b17c725F9d3FD48977", 10).toString());
    }


    public void createAsset(){
        NodeHandler nh = sv.startService().getNodeHandler();
        U.info(nh.createAssetRaw("BUTT", "Magic Butt", "buttopolis-of-magic").toString());
        U.info(nh.createAssetRaw("CHIN", "We Beat China All The Time", "trump-on-fire").toString());
        U.info(nh.createAssetRaw("XXX", "Your MOM", "sweet-ass-burn").toString());
    }


    public void getAssets(){
        NodeHandler nh = sv.startService().getNodeHandler();
        U.debugList(nh.getRegAssets());
    }


    public void getAssets2(){
        NodeHandler nh = sv.startService().getNodeHandler();
        U.debugAssetList(nh.getListAssets());
    }


    public void getAsset(){
        NodeHandler nh = sv.startService().getNodeHandler();
        U.debug(nh.getAsset("philanthropy-draw").getSupply() + "");
    }


    public void getOwner(){
        NodeHandler nh = sv.startService().getNodeHandler();
        U.debug(nh.getRegOwner());
    }







}
