package com.Rschnorr;

import com.schnorr.Generator;
import com.schnorr.PublicKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by kepuss on 20.12.15.
 */
public class Verifier {
    private Generator gen;
    private Ring ring;

    public Verifier(Generator gen, Ring ring) {
        this.gen = gen;
        this.ring = ring;
    }

    public boolean verify(Signature signature){
        //1
        BigInteger left = new BigInteger(gen.getECPoint(signature.getSigma(), gen.getG()).getEncoded(false)).mod(gen.getN());
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
        return  new BigInteger(Rmulti.add(Yhash).getEncoded(false)).mod(gen.getN());
    }
}
