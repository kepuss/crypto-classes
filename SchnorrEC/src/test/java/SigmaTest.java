import com.schnorr.Generator;
import com.schnorr.Signer;
import com.sigma.steps.Initialization;
import com.sigma.Sender;
import com.sigma.steps.Response;
import org.junit.Test;

/**
 * Created by kepuss on 04.01.16.
 */
public class SigmaTest {
    @Test
    public void ringTest(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");

        Initialization init = new Initialization(gen);
        System.out.println(Sender.send(gen, init,false));

        Signer signer = new Signer(gen.getSk(),gen);
        Response response = new Response(gen,signer,init);
        System.out.println(Sender.send(gen, response,false));
    }
}
