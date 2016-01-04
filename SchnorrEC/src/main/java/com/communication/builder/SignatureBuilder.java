package com.communication.builder;

import com.communication.model.Signature;

import java.math.BigInteger;

/**
 * Created by kepuss on 29.11.15.
 */
public class SignatureBuilder {
    final static int HEX=16;

    public static Signature getSign(com.schnorr.Signature signature){
        Signature sign = new Signature();
        sign.setE(signature.getR().toString(HEX));
        sign.setS(signature.getS().toString(HEX));
        return sign;
    }

    public static com.schnorr.Signature getSchnorrSign(Signature signature){
        com.schnorr.Signature sign = new com.schnorr.Signature();
        sign.setR(new BigInteger(signature.getE(),HEX));
        sign.setS(new BigInteger(signature.getS(),HEX));
        return sign;
    }
}
