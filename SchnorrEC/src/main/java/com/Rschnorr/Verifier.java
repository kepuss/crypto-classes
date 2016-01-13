package com.Rschnorr;

import com.Verifiable;
import com.schnorr.Generator;
import com.schnorr.Hasher;
import com.schnorr.PublicKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import com.communication.model.Signature;

/**
 * Created by kepuss on 20.12.15.
 */
public class Verifier implements Verifiable {
    private Generator gen;
    private Ring ring;
    private Signature signature;

    public Verifier(Generator gen, Ring ring, Signature signature) {
        this.gen = gen;
        this.ring = ring;
        this.signature = signature;
        System.out.println("Ring verifier init");
    }

    public boolean verify( String message){

        for(int i =0;i<signature.getH().size();i++){
            BigInteger hash = new BigInteger(1, Hasher.hash(message,signature.getR().get(i).normalize().getRawXCoord().toBigInteger(),gen.getHashName()));
            System.out.println(hash.toString(16));
            System.out.println(signature.getH().get(i).toString(16));
            if(!hash.equals(signature.getH().get(i))){
                //return false;
                System.out.println("fales");
            }
        }
        //1
        BigInteger left = new BigInteger(1,gen.getECPoint(signature.getSigma(), gen.getG()).getEncoded(false)).mod(gen.getN());
        BigInteger right = calculateRight(signature);


        System.out.println("left:  "+left);
        System.out.println("right: "+right);

        if(left.equals(right)){
            return true;
        }else{
            return false;
        }
    }

    private BigInteger calculateRight(Signature signature){
        ECPoint Rmulti = null;
        ECPoint Yhash = null;

        for(int i =0;i <ring.getAllRingSize();i++){
            if(Rmulti == null){
                Rmulti = signature.getR().get(i);
                Yhash = gen.getECPoint(signature.getH().get(i), ring.getAllRingPKs().get(i).getP());
            }else{
                Rmulti = Rmulti.add(signature.getR().get(i));
                Yhash = Yhash.add(gen.getECPoint(signature.getH().get(i), ring.getAllRingPKs().get(i).getP()));
            }
        }
        return  new BigInteger(1,Rmulti.add(Yhash).getEncoded(false)).mod(gen.getN());
    }
}
