package com.rsa;

import org.bouncycastle.pqc.math.ntru.euclid.BigIntEuclidean;

import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * Created by kepuss on 25.04.15.
 */
public class CTR {

    public static BigInteger getCTR(BigInteger[] dps , BigInteger[] p, BigInteger c, BigInteger N, BigInteger[] e,int threads) throws ExecutionException, InterruptedException {

        BigInteger sum = BigInteger.ZERO;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Callable<BigInteger>[] callTab = new Callable[p.length];
        Future<BigInteger>[] futureTab = new Future[p.length];
        long startTime = System.nanoTime();
        for(int i =0;i<p.length;i++){
            callTab[i] = new CRTthread(dps[i],p[i],c,e[i]);
            futureTab[i] = executor.submit(callTab[i]);
        }

        for(int i =0;i<p.length;i++) {
            sum = sum.add(futureTab[i].get());
        }
        long endTime = System.nanoTime();
        System.out.println( "mean time: "+(endTime - startTime));
        executor.shutdown();
        return sum.mod(N);
    }

    public static BigInteger getCTR(BigInteger[] dps , BigInteger[] p, BigInteger c, BigInteger N, BigInteger[] e){
        BigInteger sum = BigInteger.ZERO;
        for(int i =0;i<p.length;i++){
            BigInteger y= c.modPow(dps[i],p[i]);
            sum = sum.add(y.multiply(e[i]));
        }
        return sum.mod(N);
    }

    private static  BigInteger calculateE(BigInteger n, BigInteger N){
        BigIntEuclidean val = BigIntEuclidean.calculate(n, N.divide(n));
        //int[] values = EEA.getEEA(n.intValue(),N.divide(n).intValue());
        //BigInteger sel = BigInteger.valueOf(values[1]);
        BigInteger sel = val.x;
        sel = sel.multiply(N.divide(n));
        return sel;
    }
}
