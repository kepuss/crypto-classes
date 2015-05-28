package com.aesgen.modes;

import com.aesgen.cipher.CipherSchema;
import com.aesgen.cipher.IVService;
import com.aesgen.reader.FileReader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class Challenge implements WorkMode{
    @Override
    public List< byte[] > decrypt ( List< byte[] > messages, CipherSchema cipherSchema, IVService ivService, byte[] key ){
       List<byte[]> result = new ArrayList<>();
        if(messages.size() ==2){
            Random generator = new Random();
            int i =  generator.nextInt(2);
            FileReader.saveBytesToFile("output/message_B", BigInteger.valueOf(i).toByteArray());
            result.add(cipherSchema.encrypt(key,messages.get(i),ivService.getIV()));
            ivService.incrementIV();
        }
        ivService.saveIV();
        return result;
    }
}
