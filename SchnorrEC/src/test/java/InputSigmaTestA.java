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

import java.util.Scanner;

/**
 * Created by kepuss on 09.01.16.
 */
public class InputSigmaTestA {
    @Test
    public void sigmaTest(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");


        // SIDE A
        Initialization init = new Initialization(gen);
        System.out.println(Sender.send(gen, init, false));


        Scanner reader = new Scanner(System.in);
        String responseJson = reader.nextLine();
        Response response = Utils.mapObject(Response.class, responseJson);

        // SIDE A

        Verifier verifier = new Verifier(new PublicKey( Utils.getECPoint(response.getCertb().getBody().getPub_key(), gen).normalize()),
                response.getSignb(),
                gen);
        Signer signer = new Signer(gen);

        Finish last = new Finish(verifier,response,gen,init,signer);
        System.out.println(Sender.send(gen, last,false));
        System.out.println("Side A key:" + last.getK0());
    }
}
