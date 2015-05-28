package com.helios.helios.objects.vote;

import com.helios.helios.objects.election.ElgamalPublicKey;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Random;

/**
 * Created by kepuss on 16.05.15.
 */

public class ElgamalCiphertext {
    private String alpha;
    private String beta;
    private BigInteger r;

    public ElgamalCiphertext(ElgamalPublicKey pub, BigInteger m, BigInteger randomness){
        BigInteger g = new BigInteger(pub.getG());
        BigInteger p = new BigInteger(pub.getP());
        BigInteger q = new BigInteger(pub.getQ());
        BigInteger y = new BigInteger(pub.getY());

        //BigInteger m = new BigInteger(message.getBytes());
        //BigInteger m = g.pow(1);

   //     byte[] b = new byte[pub.getQBigInt().toByteArray().length];
     //   new Random().nextBytes(b);
       // r = new BigInteger(b);

       // alpha = DatatypeConverter.printBase64Binary(g.modPow(r,p).toByteArray());
       // beta = DatatypeConverter.printBase64Binary(g.modPow(m,p).multiply(y.modPow(r,p)).mod(p).toByteArray());

        alpha = g.modPow(randomness, p).toString();
       // beta = g.modPow(m,p).multiply(y.modPow(r,p)).mod(p).toString();
        beta = y.modPow(randomness,p).multiply(m).mod(p).toString();
    }

    public String getAlpha() {
        return alpha;
    }
    @JsonIgnore
    public BigInteger getAlphaBigInt() {
        return new BigInteger(alpha);
    }


    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getBeta() {
        return beta;
    }

    @JsonIgnore
    public BigInteger getBetaBigInt() {
        return new BigInteger(beta);
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

//    @JsonIgnore
//    public BigInteger getR() {
//        return r;
//    }
//    @JsonIgnore
//    public void setR(BigInteger r) {
//        this.r = r;
//    }
}
