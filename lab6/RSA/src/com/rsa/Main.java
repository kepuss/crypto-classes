package com.rsa;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        RSACBC rsacbc = new RSACBC();
//        rsacbc.run();

	    RSAScheme rsa = new RSAScheme(4);

        BigInteger input = new BigInteger("6bc1bee22e409f96e93d7e117393172ac2345435",16);
        //BigInteger input = BigInteger.valueOf(7);
        System.out.println("input " +input);
        BigInteger cipher = rsa.encrypt(input);
        System.out.println("encrypted " +cipher);
        BigInteger result = rsa.decrypt(cipher);
        System.out.println("output " + result);


    }
}
