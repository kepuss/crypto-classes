import com.Rschnorr.Ring;
import com.Utils;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import com.schnorr.Signer;
import com.schnorr.Verifier;
import com.sigma.steps.Final;
import com.sigma.steps.Finish;
import com.sigma.steps.Initialization;
import com.sigma.Sender;
import com.sigma.steps.Response;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kepuss on 04.01.16.
 */
public class RSigmaTest {
    @Test
    public void ringTest(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");

        // SIDE A
        Initialization init = new Initialization(gen);
        System.out.println(Sender.send(gen, init, false));

        // SIDE B
        Signer signer = new Signer(gen);
        Response response = new Response(gen,signer,init);
        System.out.println(Sender.send(gen, response, false));

        // SIDE A
        List<PublicKey> pklist = new ArrayList<PublicKey>();
        pklist.add(new PublicKey(Utils.getECPoint(response.getCertb().getBody().getPub_key(),gen)));
        Ring ring = new Ring(1,gen,pklist);
        com.Rschnorr.Signer Rsigner = new com.Rschnorr.Signer(ring,gen);

        Verifier verifier = new Verifier(new PublicKey( Utils.getECPoint(response.getCertb().getBody().getPub_key(), gen).normalize()),
                response.getSignb(),
                gen);

        Finish last = new Finish(verifier, response,gen,init,Rsigner);
        System.out.println(Sender.send(gen, last,false));
        System.out.println("Side A key:" + last.getK0());

        // SIDE B
        com.Rschnorr.Verifier Rverifier = new com.Rschnorr.Verifier(gen,ring,last.getSigna());
        Final fin = new Final(Rverifier, response,last,gen,init);
        System.out.println("Side B key:" + fin.getK0());
    }
}
