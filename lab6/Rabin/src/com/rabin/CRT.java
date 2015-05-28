package com.rabin;

import java.util.concurrent.*;

/**
 * Created by kepuss on 24.04.15.
 */
public class CRT {

    private long mp;
    private long mq;

    public long[] calculate(RabinScheme rabin) throws ExecutionException, InterruptedException {
        long[] results = new long[4];
        if(isSolvable(rabin.getCipher(),rabin.getP()) && isSolvable(rabin.getCipher(),rabin.getQ())) {
            threadCalculations(rabin.getP(), rabin.getQ(), rabin.getCipher());
        }else{
            if(rabin.getCipher()<=rabin.getP() && rabin.getCipher()<=rabin.getQ()) {
                mp = (long) Math.sqrt(rabin.getCipher() % rabin.getP());
                mq = (long) Math.sqrt(rabin.getCipher() % rabin.getQ());
            }
        }

            results[0] = Math.abs((rabin.getYp() * rabin.getP() * mq + rabin.getYq() * rabin.getQ() * mp) % rabin.getN());
            results[1] = Math.abs(rabin.getN() - results[0]);
            results[2] = Math.abs((rabin.getYp() * rabin.getP() * mq - rabin.getYq() * rabin.getQ() * mp) % rabin.getN());
            results[3] = Math.abs(rabin.getN() - results[2]);
    //    }
        return results;
    }

    private void threadCalculations(long p, long q, long c) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<Integer> mpThread = new SqrtRoot(p,c);
        Callable<Integer> mqThread = new SqrtRoot(q,c);

        Future<Integer> futureMq = executor.submit(mqThread);
        Future<Integer> futureMp = executor.submit(mpThread);

       mp = futureMp.get();
            mq = futureMq.get();
        executor.shutdown();
    }

    private boolean isSolvable(long a, long p){
        if(a>=p){
            System.out.println("a sould be in group");
            return false;
        }

        if(Math.pow(a,(p-1)/2) % p ==1){
            System.out.println("solvable");
            return true;
        }else{
            System.out.println("Unsolvable");
            return false;
        }
    }


}
