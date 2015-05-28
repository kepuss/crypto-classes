package com.helios.helios.objects.vote;

import com.helios.helios.objects.election.ElgamalPublicKey;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by kepuss on 16.05.15.
 */

public class Commitment {
    private String A;
    private String B;
    private BigInteger w;

    public Commitment(BigInteger A, BigInteger B){
        this.A = A.toString();
        this.B = B.toString();
        int length = 256;
        Random rand = new Random();
        w = new BigInteger(length,rand);
    }

    public Commitment(ElgamalPublicKey pub) {

        int length = pub.getQBigInt().bitLength();
        Random rand = new Random();
        w = new BigInteger(length,rand);
        A = pub.getGBigInt().modPow(w,pub.getPBigInt()).toString();
        B = pub.getYBigInt().modPow(w,pub.getPBigInt()).toString();
    }
    @JsonProperty("A")
    public String getA() {
        return A;
    }
    @JsonProperty("A")
    public void setA(String a) {
        A = a;
    }
    @JsonProperty("B")
    public String getB() {
        return B;
    }
    @JsonProperty("B")
    public void setB(String b) {
        B = b;
    }

    @JsonIgnore
    public BigInteger getW(){
        return w;
    }

    @JsonIgnore
    public BigInteger getABigInt(){
        return new BigInteger(A);
    }
    @JsonIgnore
    public BigInteger getBBigInt(){
        return new BigInteger(B);
    }
}
