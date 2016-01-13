package com.schnorr;

import com.Signable;
import com.Utils;
import com.google.common.io.BaseEncoding;
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
//ECPoint Q = Utils.getECPoint("0498FF10BF4813718F5E1C4193343C6D6E5E05AD4B0445E848660DF9B0872B0C6651E35047B92FA5991BCC7AF4650BE7D9BB93CF227A2E3FB7901C94CFB9D825C8", g);
        //2
      //  BigInteger r = new BigInteger(1,Hasher.hash(message, Q.getRawXCoord().toBigInteger(),g.getHashName())).mod(g.getN());
       // BigInteger r = new BigInteger(1,Hasher.hash(message, Q.getRawXCoord().toBigInteger(),g.getHashName())).mod(g.getN());
        BigInteger r = new BigInteger(1,Hasher.hash(message, Q.getRawXCoord().toBigInteger(),g.getHashName()));
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
        String E = BaseEncoding.base16().encode(signature.getR().toByteArray()).toUpperCase();
        String S = BaseEncoding.base16().encode(signature.getS().toByteArray()).toUpperCase();
        if(E.startsWith("00")){
            E=E.substring(2);
        }
        if(S.startsWith("00")){
            S=S.substring(2);
        }
        sign.setE(E);
        ///sign.setE(signature.getR().toString(HEX).toUpperCase());
        sign.setS(S);
        //sign.setS(signature.getS().toString(HEX).toUpperCase());
        return sign;
    }

}
