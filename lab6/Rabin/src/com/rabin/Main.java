package com.rabin;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        RabinScheme rabinScheme = new RabinScheme();
        System.out.println("n: "+rabinScheme.genKeys());
        System.out.println("p: "+rabinScheme.getP());
        System.out.println("q: "+rabinScheme.getQ());
        System.out.println("yp: "+rabinScheme.getYp());
        System.out.println("yq: "+rabinScheme.getYq());

        long c = rabinScheme.encryption(4);
        //c= rabinScheme.getPQresidue();
        System.out.println("c: "+c);
        long[] plain = rabinScheme.decryption(c);

        for(long p : plain){
            System.out.println(p);
        }
    }
}
