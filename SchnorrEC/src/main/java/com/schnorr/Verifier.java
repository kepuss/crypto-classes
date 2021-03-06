package com.schnorr;

import com.Utils;
import com.Verifiable;
import com.communication.builder.SignatureBuilder;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by kepuss on 27.10.15.
 */
public class Verifier implements Verifiable {
    private PublicKey pk;
    private Signature signature;
    private Generator generator;

    public Verifier(PublicKey pk, Signature signature,Generator genrator){
        this.pk = pk;
        this.signature=signature;
        this.generator=genrator;
    }

    public Verifier(PublicKey pk, com.communication.model.Signature signature,Generator genrator){
        this.pk = pk;
        this.signature= SignatureBuilder.getSchnorrSign(signature);
        this.generator=genrator;
    }


    public boolean verify(String message){



        System.out.println("verify message: ");
        //1
        BigInteger e = new BigInteger(1,Hasher.hash(message,signature.getR(),generator.getHashName()));
        System.out.println("e "+e.toString(16));
        //2
        ECPoint Q = generator.getECPoint(signature.getS(),generator.getG()).normalize().add(generator.getECPoint(signature.getR(),pk.getP()).normalize()).normalize();
        System.out.println("Q "+Utils.ecToString(Q));


        if(isQZero(Q)){
            return false;
        }

       // BigInteger v = new BigInteger(1,Hasher.hash(message,Q.normalize().getRawXCoord().toBigInteger(),generator.getHashName())).mod(generator.getN());
        BigInteger v = new BigInteger(1,Hasher.hash(message,Q.normalize().getRawXCoord().toBigInteger(),generator.getHashName()));
        System.out.println("v "+ v.toString(16));
        //System.out.println(signature.getR().toString(16));
        if(v.equals(signature.getR())){
            return true;
        }
        return false;
    }

    private boolean isQZero(ECPoint Q){
        if(Q.getRawXCoord().toBigInteger().equals(BigInteger.ZERO) && Q.getRawYCoord().toBigInteger().equals(BigInteger.ZERO)){
            System.out.print("Q=ZERO");
            return true;
        } else{
            return false;
        }
    }
}
