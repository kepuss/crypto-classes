package com.helios.service;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kepuss on 16.05.15.
 */
public class Crypto {
    static public String sha256AndBase64(String m) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(m.getBytes());
        return DatatypeConverter.printBase64Binary(md.digest());
    }
    static public String sha256AndBase64MinusOne(String m) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(m.getBytes());
        String result = DatatypeConverter.printBase64Binary(md.digest());
        return result.substring(0, result.length() - 1);
    }

    static public String sha1AndHex(String m) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(m.getBytes());
        String result = DatatypeConverter.printHexBinary(md.digest());
        return result.substring(0,result.length());
    }

    static public String sha256(String m) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(m.getBytes());
        return new BigInteger(1,md.digest()).toString();
    }
}
