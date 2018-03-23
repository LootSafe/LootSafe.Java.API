import io.lootsafe.api.Client;
import io.lootsafe.api.ServiceProvider;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */
public class TestClient implements Client{

    ServiceProvider sv = new ServiceProvider.ServiceBuilder()
            .withHost("http://localhost:1337/raptorstrike/")
            .withPrivateKey("dkjsdflkjdfnsf")
            .withVersion("1")
            .build();
}
