package com.schnorr;

import com.Signable;
import com.Utils;
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

    public  Signer(Generator g){
        this.sk=g.getSk();
        this.g=g;
    }

    public Signature signSig(String message){
        Signature signature = new Signature();
        //1
        BigInteger k = g.getRandomBigInt();
       ECPoint Q = g.getECPoint(k, g.getG());
//ECPoint Q = Utils.getECPoint("0444d5219a349a7137c6f292f61990476b1b08f0aa1099891bff3d6eb9046a9f65463b6f936d9f386a42f4bb744f37c0a83491b8c2b8ae9f97ad1093a7e144a6d6", g);
        //2
      //  BigInteger r = new BigInteger(1,Hasher.hash(message, Q.getRawXCoord().toBigInteger(),g.getHashName())).mod(g.getN());
        BigInteger r = new BigInteger(1,Hasher.hash(message, Q.getRawXCoord().toBigInteger(),g.getHashName())).mod(g.getN());
//        System.out.println("------------r--- "+r.toString(16));
//        System.out.println("------------message--- "+message);
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
