package com.schnorr;

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
        //1
        BigInteger e = new BigInteger(Hasher.hash(message,signature.getR(),generator.getHashName()));
        //2
        ECPoint Q = generator.getECPoint(signature.getS(),generator.getG()).normalize().add(generator.getECPoint(signature.getR(),pk.getP()).normalize()).normalize();

        if(isQZero(Q)){
            return false;
        }

        BigInteger v = new BigInteger(Hasher.hash(message,Q.getRawXCoord().toBigInteger(),generator.getHashName())).mod(generator.getN());

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
