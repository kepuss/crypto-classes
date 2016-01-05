package com.sigma.steps;

import com.Verifiable;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import com.schnorr.Verifier;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by kepuss on 05/01/16.
 */
public class Final {

    public Final(Response response, Generator gen, Initialization init) {
        Verifier verifier = new Verifier(new PublicKey(getEcPoint(response.getCertb().getBody().getPub_key(),gen)),
                response.getSignb(),
                gen);
        System.out.println(verify(verifier,init,response.getEphy()));



    }


    private ECPoint getEcPoint(String point, Generator gen) {
        String pk = point.substring(2);
        String pkX = pk.substring(0, pk.length() / 2);
        String pkY = pk.substring((pk.length() / 2), pk.length());
        return gen.getCurve().getCurve().createPoint(new BigInteger(pkX, 16), new BigInteger(pkY, 16));
    }

    private boolean verify(Verifier verifier, Initialization init, String ephy){
        String payload = "00" + init.getSession() + ephy + init.getEphx();
        return verifier.verify(payload);
    }
}
