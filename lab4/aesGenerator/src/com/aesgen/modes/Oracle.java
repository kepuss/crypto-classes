package com.aesgen.modes;

import com.aesgen.cipher.CipherSchema;
import com.aesgen.cipher.IVService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class Oracle implements WorkMode{


    @Override
    public List< byte[] > decrypt ( List< byte[] > messages , CipherSchema cipherSchema, IVService ivService, byte[] key){

        List<byte[]> results = new ArrayList<>();
        for(byte[] message : messages){
            results.add(cipherSchema.encrypt(key,message,ivService.getIV()));
            ivService.incrementIV();
        }
        ivService.saveIV();
        return results;

    }
}
