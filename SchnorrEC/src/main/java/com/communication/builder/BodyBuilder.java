package com.communication.builder;

import com.communication.model.Body;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by kepuss on 29.11.15.
 */
public class BodyBuilder {
    final static int HEX=16;

    public static Body getBody(String keyholder, PublicKey pk, String par, String hash, String ca){
        Body body = new Body();
        body.setPar(par);
        body.setCa_name(ca);
        body.setHash(hash);
        body.setKey_holder(keyholder);
        String Xcord = pk.getP().normalize().getRawXCoord().toBigInteger().toString(HEX);
        String Ycord = pk.getP().normalize().getRawYCoord().toBigInteger().toString(HEX);
        String pub_key=("04"+Xcord+Ycord).toUpperCase();
        body.setPub_key(pub_key);
        return body;
    }

    public static Generator getGenerator(Body body){

        Generator gen = new Generator(body.getPar(),body.getHash());
        String pk = body.getPub_key().substring(2);
        String pkX= pk.substring(0, pk.length() / 2);
        String pkY= pk.substring((pk.length() / 2) , pk.length());
        ECPoint P = gen.getCurve().getCurve().createPoint(new BigInteger(pkX, HEX), new BigInteger(pkY, HEX));
        PublicKey publicKey = new PublicKey();
        publicKey.setP(P);
        gen.setPk(publicKey);
        return gen;
    }

}
