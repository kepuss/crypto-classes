package com.aesgen.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class AESCBC implements CipherSchema{
    private Cipher cipher;
    private final String ALGORITHM = "AES/CBC/NoPadding";

    public AESCBC (){
        try{
            cipher = Cipher.getInstance(ALGORITHM ,"BC");
        } catch ( NoSuchAlgorithmException e ){
            e.printStackTrace();
        } catch ( NoSuchPaddingException e ){
            e.printStackTrace();
        } catch ( NoSuchProviderException e ){
            e.printStackTrace();
        }
    }



    public byte[] encrypt ( byte[] keyByte, byte[] message, byte[] IV ){
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyByte,ALGORITHM ) ;
        byte[] result = null;
        try{
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec, new IvParameterSpec(IV));
            result = cipher.doFinal(message);
        } catch ( InvalidKeyException e ){
            e.printStackTrace();
        } catch ( BadPaddingException e ){
            e.printStackTrace();
        } catch ( IllegalBlockSizeException e ){
            e.printStackTrace();
        } catch ( InvalidAlgorithmParameterException e ){
            e.printStackTrace();
        }
        return result;
    }


    public byte[] decrypt ( byte[] keyByte, byte[] message , byte[] IV ){
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyByte,ALGORITHM ) ;
        byte[] result = null;
        try{
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec, new IvParameterSpec(IV));
            result = cipher.doFinal(message);
        } catch ( InvalidKeyException e ){
            e.printStackTrace();
        } catch ( BadPaddingException e ){
            e.printStackTrace();
        } catch ( IllegalBlockSizeException e ){
            e.printStackTrace();
        } catch ( InvalidAlgorithmParameterException e ){
            e.printStackTrace();
        }
        return result;
    }
}
