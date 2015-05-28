package com.rabin;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by kepuss on 24.04.15.
 */
public class SqrtRoot implements Callable<Integer>{
    private BigInteger a;
    private BigInteger p;
    private int l;
    private int m;
    private BigInteger res;

    public SqrtRoot(long p, long c){
        this.a =BigInteger.valueOf(c);
        this.p=BigInteger.valueOf(p);
    }

    public Integer call(){
        //res =BigInteger.valueOf((p - 1)/2);
        res = p.subtract(BigInteger.valueOf(1));
              res=  res.divide(BigInteger.valueOf(2));
        calMandL(res);
        BigInteger r = res;
        BigInteger rPrim =BigInteger.valueOf(0);
        BigInteger b = nonResidue();
        for(long i = l;i>0;i--){
            r=r.divide(BigInteger.valueOf(2));
            rPrim = rPrim.divide(BigInteger.valueOf(2));
            BigInteger tmpA = a.modPow(r,p);
            BigInteger tmpB = b.modPow(rPrim,p);
            tmpA=tmpA.multiply(tmpB);
            tmpA = tmpA.mod(p);

            BigInteger tmpP = p.subtract(BigInteger.valueOf(1));

            if(tmpA.intValue() ==  tmpP.intValue()){
      //      if((Math.pow(a,r) * Math.pow(b,rPrim))%p == p-1){
                rPrim = rPrim.add(res);
            }
        }
        BigInteger amod = a;
        //amod = amod.modPow(BigInteger.valueOf((r + 1) / 2),BigInteger.valueOf(p));
        r = r.add(BigInteger.valueOf(1));
        BigInteger aExp = r.divide(BigInteger.valueOf(2));
        amod = amod.modPow(aExp, p);
   //     amod = amod.pow(aExp.intValue());
   //     amod = amod.mod(p);

        BigInteger bmod = b;
       // bmod = bmod.modPow(BigInteger.valueOf(rPrim / 2), BigInteger.valueOf(p));
        bmod = bmod.modPow(rPrim.divide(BigInteger.valueOf(2)),p);

        amod = amod.multiply(bmod);
        amod = amod.mod(p);

        return amod.intValue();

//        long aa = (long)Math.pow(a, (r + 1) / 2);
//        long bb = (long)Math.pow(b, rPrim / 2);
//        Integer out =Integer.valueOf((int)(Math.pow(a, (r + 1) / 2) * Math.pow(b, rPrim / 2))%p);
//        return out;
    }

    private BigInteger nonResidue(){
        Random random = new Random();
        int b = 1;
        BigInteger bg = BigInteger.valueOf(b);
        while(!( bg.modPow(res,p).intValue() == p.subtract(BigInteger.valueOf(1)).intValue() )){
            bg = BigInteger.valueOf(++b);
        }
        return bg;
    }

    private void calMandL(BigInteger res){
        BigInteger b1 = res;
        BigInteger b2 = BigInteger.valueOf(2);
        int counter =0;
        BigInteger gcd = b1.gcd(b2);
        while(gcd.intValue() == 2){
            counter++;
            b1 = b1.divide(b2);
            gcd = b1.gcd(b2);
        }
        l=counter;
        m=b1.intValue();
    }
}
