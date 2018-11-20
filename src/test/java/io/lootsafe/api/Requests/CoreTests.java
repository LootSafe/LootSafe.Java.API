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

    @Test
    public void GetMetadata(){
        NodeHandler nh = sv.startService().getNodeHandler();
        U.info(nh.getMetadataRaw().toString());
    }


}