import com.Utils;
import com.communication.model.Body;
import com.communication.model.Certificate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;

/**
 * Created by kepuss on 08.01.16.
 */
public class SigmaMartaTest {

    private String RESPONSE = "{\"ephy\":\"041B0C1A6FCEE2630715F3673A6363DDF41D4ED722FA6BFBC7824DCD589B7AD6FB90A533745ECC7DB0FFAD8379A65DF23CBD25D1502FDFF4B7998D53877939D174\"," +
            "\"certb\":{\"body\":{\"ca_name\":\"dummy\",\"hash\":\"SHA-256\",\"key_holder\":\"mmul\",\"par\":\"brainpoolp256r1\"," +
            "\"pub_key\":\"041A57F9C59DD57F0EF4DEB68C7DE221A1804923CCB0903566161EF4D8675DB81444FF373EED33A21F3298E0F50E8FC782BDF859284AD70AB6AB6C02519E3C35BC\"}," +
            "\"sign\":{\"e\":\"FFFAAA024403BEF102433222222222\",\"s\":\"ACCB14900222BCA3942BA000\"}}," +
            "\"signb\":{\"e\":\"441782545F0FC1FE84A25E943A72FAF7C4EB36CFDF167404CED815D2B60E4AF3\",\"s\":\"0089E0A432738DD7D09726F8A2959E3FE9A942BB0FC06F731E4F0FDD6955F11E6A\"}," +
            "\"macb\":\"E29023E60D0AFC4891F44CCABA88577EA2D6A1487E3B600E37290DB4F0FD4003\",\"session\":\"75C014CD16F008664E60CE93E187043D\"}";

    @Test
    public void sigmaTest(){

        BigInteger Y = new BigInteger("1732600003780434483233506545296899843334966299070987905792457589388026771829");
        BigInteger X = new BigInteger("73246574710268087090324911967279444333339317441888111830190771098948520286872");
        String session = "75C014CD16F008664E60CE93E187043D";

        BigInteger gen_sk = new BigInteger("00950a1deaa15ee8d68a8973522ebca8a523832da7ee44adeec077d61e5e74ddea",16);
        BigInteger gen2_sk = new BigInteger("009fc8be0ef4897ae6c4cedbba32d2d70cfa0bc6b4ca04200ca9614a3e3667e07a",16);


        Generator gen = new Generator("brainpoolP256r1","SHA-256",gen_sk);

        // SIDE A
        Initialization init = new Initialization(gen,X,session);
        System.out.println("\n");
        System.out.println(Sender.send(gen, init, false));
        System.out.println("\n");

        // SIDE B
        Generator gen2 = new Generator("brainpoolP256r1","SHA-256",gen2_sk);
        Signer signer = new Signer(gen2);
        Response response = new Response(gen2,signer,init,Y,getMartaCert().getCertb());
        System.out.println("\n");
        System.out.println(Sender.send(gen2, response, false));
        System.out.println("\n");

        // SIDE A

//        Verifier verifier = new Verifier(new PublicKey( Utils.getECPoint(response.getCertb().getBody().getPub_key(), gen2).normalize()),
//                response.getSignb(),
//                gen2);
        Verifier verifier = new Verifier(new PublicKey( gen2.getPk().getP()),
                response.getSignb(),
                gen2);

        Signer signerA = new Signer(gen);
        Finish last = new Finish(verifier,response,gen,init,signerA);
        System.out.println(Sender.send(gen, last,false));
        System.out.println("Side A key:" + last.getK0());

        // SIDE B
        Verifier verifierB = new Verifier(new PublicKey( gen.getPk().getP()),
                last.getSigna(),
                gen);
        Final fin = new Final(verifierB, response,last,gen,init);
        System.out.println("Side B key:" + fin.getK0());
    }

    private Response getMartaCert(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true);
        try {
            return mapper.readValue(RESPONSE, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
