import com.schnorr.Generator;
import com.sigma.steps.Initialization;
import com.sigma.Sender;
import org.junit.Test;

/**
 * Created by kepuss on 04.01.16.
 */
public class SigmaTest {
    @Test
    public void ringTest(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");

        System.out.print(Sender.send(gen, new Initialization(gen),false));
    }
}
