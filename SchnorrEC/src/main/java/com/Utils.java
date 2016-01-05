package com;

import com.schnorr.Generator;
import com.sigma.steps.Initialization;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * Created by kepuss on 05.01.16.
 */
public class Utils {
    final static BigInteger OPAD = new BigInteger("47474747474747474747474747474747", 16);
    final static BigInteger IPAD = new BigInteger("74747474747474747474747474747474", 16);


    public static byte[] computeMac(byte[] message, BigInteger key) {
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//        System.out.println(key.);
//        System.out.println(OPAD.bitLength());
//        System.out.println(key.toString(16));
//        System.out.println(OPAD.toString(16));


        BigInteger xorOpad = key.xor(OPAD);
        BigInteger xorIpad = key.xor(IPAD);

        byte[] ipadMessage = Arrays.concatenate(xorIpad.toByteArray(), message);

        byte[] firstHash = hash.digest(ipadMessage);

        byte[] finalHashInput = Arrays.concatenate(xorOpad.toByteArray(), firstHash);

        return hash.digest(finalHashInput);

    }

    public static ECPoint getECPoint(String point, Generator gen) {
      //  System.out.println(point);
        String pk = point.substring(2);
        String pkX = pk.substring(0, pk.length() / 2);
        String pkY = pk.substring((pk.length() / 2), pk.length());
     //   System.out.println(new BigInteger(pkX, 16).toString(16));
     //   System.out.println(new BigInteger(pkY, 16).toString(16));

        return gen.getCurve().getCurve().createPoint(new BigInteger(pkX, 16), new BigInteger(pkY, 16));
    }

    public static byte[] KDF(ECPoint K,int i){
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] input = Arrays.append(ecToString(K).getBytes(), (byte) i);
        byte[] output = hash.digest(input);

//        System.out.println(new BigInteger(1, Arrays.copyOfRange(output,0,16)).toString(16));
    //    System.out.println(new String(Arrays.copyOf(output,128)));
// 16 because 128/6=16
        return Arrays.copyOfRange(output,0,16);
    }

    public static String ecToString(ECPoint p){
        String cordX = new BigInteger(1,p.normalize().getAffineXCoord().getEncoded()).toString(16);
        String cordY = new BigInteger(1,p.normalize().getAffineYCoord().getEncoded()).toString(16);
        if(cordX.length() > cordY.length()){
            cordY="0"+cordY;
        }
        if(cordY.length() > cordX.length()){
            cordX="0"+cordX;
        }
        return "04"+ cordX+cordY;
    }
}
