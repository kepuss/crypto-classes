package com.schnorr;

import com.google.common.io.BaseEncoding;
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

        //md.update(Arrays.concatenate(message.getBytes(), R.toByteArray()));
//        System.out.println(message.getBytes().length);
//        System.out.println(R.toString(16).getBytes().length);
//        System.out.println(BaseEncoding.base16().encode(R.toByteArray()));
        String Rhex = BaseEncoding.base16().encode(R.toByteArray());
        System.out.println("-------------" + Rhex);
        if(Rhex.startsWith("00") && Rhex.length() != 64){
            Rhex = Rhex.substring(2);
        }
        System.out.println("-------------"+Rhex);

        //byte[] input = BaseEncoding.base16().decode((message + BaseEncoding.base16().encode(R.toByteArray())).toUpperCase());
        byte[] input = BaseEncoding.base16().decode((message + Rhex).toUpperCase());
        System.out.println("input: "+BaseEncoding.base16().encode(input));
        md.update(input);
        return md.digest();
    }
}
