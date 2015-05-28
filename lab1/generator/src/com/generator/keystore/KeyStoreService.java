package com.generator.keystore;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/7/15.
 *
 *         keytool -list -v -keystore newKeyStoreName -storetype BKS -providerclass org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath /home/kepuss/Downloads/bcprov-debug-jdk15on-152.jar

 */
public class KeyStoreService{
    private KeyStore keyStore;
    private final String FILENAME = "keyStore";
    private final String KEYSTOREPASS = "password";
    private final String ALIAS = "myAlias";

    public KeyStoreService (){
        try{
            FileInputStream file = new FileInputStream(FILENAME);
            keyStore = KeyStore.getInstance("BKS");
            keyStore.load(file, KEYSTOREPASS.toCharArray());
            file.close();
        } catch ( KeyStoreException e ){
            e.printStackTrace();
        } catch ( FileNotFoundException e ){
            e.printStackTrace();
        } catch ( NoSuchAlgorithmException e ){
            e.printStackTrace();
        } catch ( CertificateException e ){
            e.printStackTrace();
        } catch ( IOException e ){
            e.printStackTrace();
        }
    }

    public byte[] getKey (){
        try{
            char[] password = KEYSTOREPASS.toCharArray();

            KeyStore.SecretKeyEntry secretKeyEntry = ( KeyStore.SecretKeyEntry )
                    keyStore.getEntry(ALIAS, new KeyStore.PasswordProtection(password));

            return secretKeyEntry.getSecretKey().getEncoded();
        } catch ( KeyStoreException e ){
            e.printStackTrace();
        } catch ( NoSuchAlgorithmException e ){
            e.printStackTrace();
        } catch ( UnrecoverableKeyException e ){
            e.printStackTrace();
        } catch ( UnrecoverableEntryException e ){
            e.printStackTrace();
        }
        return null;
    }

    public void storeKey (){
        SecretKey key = null;
        try{
            key = KeyGenerator.getInstance("AES", "BC").generateKey();

            KeyStore.SecretKeyEntry skEntry =
                    new KeyStore.SecretKeyEntry(key);
            keyStore.setEntry(ALIAS, skEntry,
                    new KeyStore.PasswordProtection(KEYSTOREPASS.toCharArray()));

            java.io.FileOutputStream fos = null;
            try{
                fos = new java.io.FileOutputStream(FILENAME);
                keyStore.store(fos, KEYSTOREPASS.toCharArray());
            } finally{
                if ( fos != null ){
                    fos.close();
                }
            }
        } catch ( NoSuchAlgorithmException e ){
            e.printStackTrace();
        } catch ( KeyStoreException e ){
            e.printStackTrace();
        } catch ( FileNotFoundException e ){
            e.printStackTrace();
        } catch ( IOException e ){
            e.printStackTrace();
        } catch ( CertificateException e ){
            e.printStackTrace();
        } catch ( NoSuchProviderException e ){
            e.printStackTrace();
        }
    }
}
