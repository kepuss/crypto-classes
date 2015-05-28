package com.rsa;

import org.apache.commons.math3.primes.Primes;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by kepuss on 24.04.15.
 */
public class PrimeGen {

    public static BigInteger getPrime(){
        Random rGen = new Random();
        BigInteger bigProb = BigInteger.probablePrime(512, rGen);
        //int r = rGen.nextInt(8000);
//        if(r<0){
//            r*=-1;
//        }
        return bigProb;
    }

    public static int getPrime(BigInteger num){
        Random rGen = new Random();
        int r = rGen.nextInt(num.intValue());
        if(r<0){
            r*=-1;
        }
        return Primes.nextPrime(r);
    }
}
