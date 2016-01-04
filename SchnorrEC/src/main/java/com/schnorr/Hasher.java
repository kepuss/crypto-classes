package com.schnorr;

import org.bouncycastle.util.Arrays;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kepuss on 27.10.15.
 */
public class Hasher {
    public static byte[] hash(String message, BigInteger R, String hashName){
        MessageDigest md=null;
        try {
            md = MessageDigest.getInstance(hashName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(Arrays.concatenate(message.getBytes(), R.toByteArray()));
        return md.digest();
    }
}
