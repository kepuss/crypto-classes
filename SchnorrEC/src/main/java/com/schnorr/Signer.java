package com.schnorr;

import com.Signable;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by kepuss on 27.10.15.
 */
@Getter
@Setter
public class Signer implements Signable{
    private String message;
    private PrivateKey sk;
    private Generator g;

    final int HEX=16;

    public  Signer(PrivateKey sk, Generator g){
        this.sk=sk;
        this.g=g;
    }

    public Signature signSig(String message){
        Signature signature = new Signature();
        //1
        BigInteger k = g.getRandomBigInt();
        ECPoint Q = g.getECPoint(k, g.getG());


        //2
        BigInteger r = new BigInteger(1,Hasher.hash(message, Q.getRawXCoord().toBigInteger(),g.getHashName())).mod(g.getN());
        //3
        BigInteger s =  k.subtract(sk.getA().multiply(r)).mod(g.getN());

        //4
        signature.setR(r);
        signature.setS(s);
        return signature;
    }

    public com.communication.model.Signature sign(String message){
        Signature signature = signSig(message);
        com.communication.model.Signature sign = new com.communication.model.Signature();
        sign.setE(signature.getR().toString(HEX));
        sign.setS(signature.getS().toString(HEX));
        return sign;
    }

}
