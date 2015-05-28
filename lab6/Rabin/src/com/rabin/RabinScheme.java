package com.rabin;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by kepuss on 24.04.15.
 */
public class RabinScheme {
    private int p=131;
    private int q=29;
    private long n =-1;
    private long yp;
    private long yq;
    private long cipher;
    private final int MODULUS_REST=1;
    private final int MODULUS_DIV=4;

    public long genKeys(){
        while(n<99) {
            p = PrimeGen.getPrime();
            while (!isSuitsMod(p)) {
                p = PrimeGen.getPrime();
            }

            q = PrimeGen.getPrime();
            while (!isSuitsMod(q) || p==q) {
                q = PrimeGen.getPrime();
            }
           n = p * q;
        }

        int[] euclides = EEA.getEEA(p,q);
        yp = euclides[0];
        yq = euclides[1];
        return n;
    }

    private boolean isSuitsMod(long prime){
        if(prime % MODULUS_DIV == MODULUS_REST){
            return true;
        }else{
            return false;
        }
    }

    public long encryption(long message){
        return (message * message) % n;
    }

    public long[] decryption(long cipher) throws ExecutionException, InterruptedException {
        this.cipher = cipher;
        CRT crt = new CRT();
        return crt.calculate(this);
    }

    public int getPQresidue(){
        Random random = new Random();
        int min=0;
        if(p<q){
            min=p;
        }else{
            min=q;
        }
        int r = random.nextInt(min);
        while(!((Math.pow(r,(p-1)/2)%p ==1) && (Math.pow(r,(q-1)/2)%q ==1) && r>1)){
            r = random.nextInt(min);
        }
        return r;
    }

    public long getP() {
        return p;
    }

    public long getQ() {
        return q;
    }

    public long getYp() {
        return yp;
    }

    public long getYq() {
        return yq;
    }

    public long getCipher() {
        return cipher;
    }

    public long getN() {
        return n;
    }
}
