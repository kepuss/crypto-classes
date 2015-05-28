package com.rsa;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * Created by kepuss on 25.04.15.
 */
public class CRTthread implements Callable<BigInteger> {
    BigInteger dps;
    BigInteger p;
    BigInteger c;
 //   BigInteger N;
    BigInteger e;

    CRTthread(BigInteger dps , BigInteger p, BigInteger c, BigInteger e){
        this.dps=dps;
        this.p = p;
        this.c=c;
 //       this.N = N;
        this.e=e;
    }

    public BigInteger call(){
        BigInteger y= c.modPow(dps,p);
        return y.multiply(e);
    }

//    private BigInteger calculateE(BigInteger n, BigInteger N){
//        int[] values = EEA.getEEA(n.intValue(),N.divide(n).intValue());
//        BigInteger sel = BigInteger.valueOf(values[1]);
//        sel = sel.multiply(N.divide(n));
//        return sel;
//    }
}
