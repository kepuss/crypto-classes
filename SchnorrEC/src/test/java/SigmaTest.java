import com.Utils;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import com.schnorr.Signer;
import com.schnorr.Verifier;
import com.sigma.Sender;
import com.sigma.steps.Final;
import com.sigma.steps.Finish;
import com.sigma.steps.Initialization;
import com.sigma.steps.Response;
import org.junit.Test;

/**
 * Created by kepuss on 06.01.16.
 */
public class SigmaTest {
    @Test
    public void sigmaTest(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");

        // SIDE A
        Initialization init = new Initialization(gen);
        System.out.println(Sender.send(gen, init, false));

        // SIDE B
        Signer signer = new Signer(gen);
        Response response = new Response(gen,signer,init);
        System.out.println(Sender.send(gen, response, false));

        // SIDE A

        Verifier verifier = new Verifier(new PublicKey( Utils.getECPoint(response.getCertb().getBody().getPub_key(), gen).normalize()),
                response.getSignb(),
                gen);
        Finish last = new Finish(verifier,response,gen,init,signer);
        System.out.println(Sender.send(gen, last,false));
        System.out.println("Side A key:" + last.getK0());

        // SIDE B
        Verifier verifierB = new Verifier(new PublicKey( Utils.getECPoint(last.getCerta().getBody().getPub_key(), gen).normalize()),
                last.getSigna(),
                gen);
        Final fin = new Final(verifierB, response,last,gen,init);
        System.out.println("Side B key:" + fin.getK0());
    }
}
