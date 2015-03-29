package com.generator.schemes;

import com.generator.charsets.CustomCharset;
import com.generator.keystore.KeyStoreService;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.engines.Salsa20Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/7/15.
 */
public class Salsa20Scheme implements Scheme{
    private StreamCipher streamCipher;
    private KeyParameter keyParameter;
    private byte[] keyBytes;
    private final int OFFSET;

    public Salsa20Scheme(){

        OFFSET = 0;

        streamCipher = new Salsa20Engine();
        KeyStoreService keyStoreService = new KeyStoreService();
        keyBytes = keyStoreService.getKey();
        keyParameter = new KeyParameter(Arrays.copyOf(keyBytes,16));
        ParametersWithIV piv=new ParametersWithIV(keyParameter,"12345678".getBytes());
        streamCipher.init(true,piv);
    }

    @Override
    public String cipher ( String message ,CustomCharset customCharset) {
        byte[] messageByte = message.getBytes(Charset.forName(customCharset.toString()));
        byte[] resultByte = new byte[messageByte.length];
        streamCipher.processBytes(messageByte,OFFSET,messageByte.length,resultByte,OFFSET);
        try{
            String result = new String(resultByte,customCharset.toString());
            return result;
        } catch ( UnsupportedEncodingException e ){
            e.printStackTrace();
        }
        return null;
    }
}
