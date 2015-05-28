package com.rabin;

import org.apache.commons.math3.primes.Primes;

import java.util.Random;

/**
 * Created by kepuss on 24.04.15.
 */
public class PrimeGen {

    public static int getPrime(){
        Random rGen = new Random();
        int r = rGen.nextInt(400);
        if(r<0){
            r*=-1;
        }
        return Primes.nextPrime(r);
    }
}
