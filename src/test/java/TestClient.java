import io.lootsafe.api.Client;
import io.lootsafe.api.ServiceProvider;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */
public class TestClient implements Client{


    public static void main(String [] args){
        ServiceProvider sv = new ServiceProvider.ServiceBuilder()
                .withHost("http://localhost:1337/raptorstrike/")
                .withPrivateKey("dkjsdflkjdfnsf")
                .withVersion("1")
                .build();
        sv.startService();
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
