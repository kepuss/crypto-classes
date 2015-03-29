package com.generator.schemes;

import com.generator.charsets.CustomCharset;
import com.generator.keystore.KeyStoreService;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/6/15.
 */
public class RC4Scheme implements Scheme{
    private StreamCipher streamCipher;
    private KeyParameter keyParameter;
    private byte[] keyBytes;
    private final int OFFSET;

    public RC4Scheme(){

        OFFSET = 0;

        streamCipher = new RC4Engine();
        KeyStoreService keyStoreService = new KeyStoreService();
        keyBytes = keyStoreService.getKey();
        keyParameter = new KeyParameter(keyBytes);
        streamCipher.init(true,keyParameter);
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
