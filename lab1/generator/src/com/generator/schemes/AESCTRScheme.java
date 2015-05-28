package com.generator.schemes;

import com.generator.charsets.CustomCharset;
import com.generator.keystore.KeyStoreService;
import com.sun.crypto.provider.AESParameters;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.params.KeyParameter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/7/15.
 */
public class AESCTRScheme implements Scheme{
    private BlockCipher aesBlockCipher;
    private BlockCipher ctrBlockCipher;
    private KeyParameter keyParameter;
    private byte[] keyBytes;
    private final int OFFSET;

    public AESCTRScheme(){

        OFFSET = 0;

        aesBlockCipher = new AESEngine();
        AESParameters parameters = new AESParameters();//"AES/CTR/NoPadding");
        KeyStoreService keyStoreService = new KeyStoreService();
        keyBytes = keyStoreService.getKey();
        keyParameter = new KeyParameter(keyBytes);
        aesBlockCipher.init(true, keyParameter);
    }

    @Override
    public String cipher ( String message ,CustomCharset customCharset) {
        byte[] messageByte = message.getBytes(Charset.forName(customCharset.toString()));
        byte[] resultByte = new byte[messageByte.length];
        aesBlockCipher.processBlock(messageByte, OFFSET, resultByte, OFFSET);
        try{
            String result = new String(resultByte,customCharset.toString());
            return result;
        } catch ( UnsupportedEncodingException e ){
            e.printStackTrace();
        }
        return null;
    }
}
