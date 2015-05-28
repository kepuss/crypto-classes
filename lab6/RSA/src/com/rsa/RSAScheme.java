package com.rsa;

import org.bouncycastle.pqc.math.ntru.euclid.BigIntEuclidean;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by kepuss on 25.04.15.
 */
public class RSAScheme {
    BigInteger[] ps;
    BigInteger[] dps;
    BigInteger[] eThreads;
    BigInteger n;
    BigInteger totient;
    BigInteger e;
    BigInteger d;

    public RSAScheme(int k){
        ps = new BigInteger[k];
        eThreads = new BigInteger[k];
        dps = new BigInteger[k];
        n= BigInteger.valueOf(1);
        //ps[0]=BigInteger.valueOf(61);
        //ps[1]=BigInteger.valueOf(53);

        for(int i=0;i<k;i++){
            ps[i] = PrimeGen.getPrime();
            System.out.println(ps[i]);
            n = n.multiply(ps[i]);
        }

        for(int i=0;i<k;i++){
            eThreads[i] = calculateE(ps[i], n);
        }

        totient = getTotient(n,ps);
        e = coprime(totient);
        d = getModularInverse(e,totient);
        for(int i=0;i<k;i++){
            dps[i] = d.mod(ps[i].subtract(BigInteger.ONE));
        }
    }

    private static BigInteger getTotient(BigInteger num, BigInteger[] ps){
        BigInteger totient=BigInteger.valueOf(1);

        for(int a=0;a<ps.length;a++){
            totient = totient.multiply(ps[a].subtract(BigInteger.ONE));
        }
        return totient;
    }

    private BigInteger coprime(BigInteger num){
        Random random = new Random();
        BigInteger r = BigInteger.probablePrime(128,random);
        while(!(num.gcd(r).intValue() == 1)){
            r = BigInteger.probablePrime(128, random);
        }
        return r;
       // return BigInteger.valueOf(17);
    }

    private BigInteger getModularInverse(BigInteger exp, BigInteger tot){
       // BigIntEuclidean result = BigIntEuclidean.calculate(exp, tot);
        BigInteger result = exp.modInverse(tot);
        return result;
        //return result.x.mod(result.y);

//        BigInteger temp;
//        for(int i =0;i<tot.intValue()-1;i++){
//            temp = exp.multiply(BigInteger.valueOf(i));
//            temp = temp.mod(tot);
//            if(temp.intValue() == 1){
//                return BigInteger.valueOf(i);
//            }
//        }
//        return null;
    }

    public BigInteger encrypt(BigInteger message){
        return message.modPow(e,n);
    }

    public BigInteger decrypt(BigInteger cipher) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        BigInteger threadResult  = CTR.getCTR(dps, ps, cipher, n, eThreads,4);
        long endTime = System.currentTimeMillis();
        System.out.println("CRT thread: "+ threadResult+" time: "+(endTime - startTime));

        startTime = System.currentTimeMillis();
        BigInteger CRTresult =  CTR.getCTR(dps, ps, cipher, n,eThreads);
        endTime = System.currentTimeMillis();
        System.out.println("CRT: "+ CRTresult+" time: "+(endTime - startTime));

        startTime = System.currentTimeMillis();
        BigInteger result =  cipher.modPow(d, n);
        endTime = System.currentTimeMillis();
        System.out.println("normal: "+ result+" time: "+(endTime - startTime));

        return result;
    }

    private BigInteger calculateE(BigInteger n, BigInteger N){
        BigIntEuclidean result = BigIntEuclidean.calculate(n, N.divide(n));
        BigInteger sel = result.y;
        sel = sel.multiply(N.divide(n));
        return sel;
    }
}
