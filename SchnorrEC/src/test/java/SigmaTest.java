import com.schnorr.Generator;
import com.schnorr.Signer;
import com.sigma.steps.Final;
import com.sigma.steps.Finish;
import com.sigma.steps.Initialization;
import com.sigma.Sender;
import com.sigma.steps.Response;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by kepuss on 04.01.16.
 */
public class SigmaTest {
    @Test
    public void ringTest(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");

        // SIDE A
        Initialization init = new Initialization(gen);
        System.out.println(Sender.send(gen, init,false));

        // SIDE B
        Signer signer = new Signer(gen.getSk(),gen);
        Response response = new Response(gen,signer,init);
        System.out.println(Sender.send(gen, response,false));

        // SIDE A
        Finish last = new Finish(response,gen,init,signer);
        System.out.println(Sender.send(gen, last,false));
        System.out.println("Side A key:" + last.getK0().toString(16));

        // SIDE B
        Final fin = new Final(response,last,gen,init);
        System.out.println("Side B key:" + fin.getK0().toString(16));
    }
}
