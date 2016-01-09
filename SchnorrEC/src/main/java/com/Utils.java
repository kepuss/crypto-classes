package com;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.BaseEncoding;
import com.schnorr.Generator;
import com.sigma.steps.Initialization;
import com.sigma.steps.Response;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * Created by kepuss on 05.01.16.
 */
public class Utils {
    final static byte[] OPAD = BaseEncoding.base16().decode("47474747474747474747474747474747");
    final static byte[] IPAD = BaseEncoding.base16().decode("74747474747474747474747474747474");


    public static byte[] computeMac(byte[] message, byte[] key) {
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


        byte[] xorOpad = xorByte(key, OPAD);
        byte[] xorIpad = xorByte(key,IPAD);

        byte[] ipadMessage = Arrays.concatenate(xorIpad, message);

        byte[] firstHash = hash.digest(ipadMessage);

        byte[] finalHashInput = Arrays.concatenate(xorOpad, firstHash);

        return hash.digest(finalHashInput);

    }

    private static byte[] xorByte(byte[] a, byte[] b){
        if(a.length != b.length){
            throw new RuntimeException("Size not match");
        }
        byte [] result = new byte[a.length];
        for(int i =0; i<a.length;i++){
            result[i] = (byte)(a[i]^b[i]);
        }
        return result;

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

    public static byte[] KDF(ECPoint K,String suffix){
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] input = BaseEncoding.base16().decode(Utils.ecToString(K).toUpperCase() + suffix);
        byte[] output = hash.digest(input);

// 16 because 128/6=16
        return Arrays.copyOfRange(output, 0, 16);
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

    public static <T> T mapObject(Class<T> object, String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true);
        try {
            return mapper.readValue(json, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
