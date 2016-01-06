
import com.Rschnorr.Ring;

import com.Rschnorr.Signer;
import com.Rschnorr.Verifier;
import com.communication.model.Signature;
import com.schnorr.Generator;
import org.junit.Test;

/**
 * Created by kepuss on 20.12.15.
 */
public class RingTest {
    @Test
    public void ringTest(){
        Generator gen = new Generator("P-256","SHA-256");
        Ring ring = new Ring(gen,10);


        Signer signer = new Signer(ring,gen);
        Signature signature = signer.sign("Message");


        Verifier verifier = new Verifier(gen,ring,signature);
        System.out.print(verifier.verify("Message"));
    }
}
