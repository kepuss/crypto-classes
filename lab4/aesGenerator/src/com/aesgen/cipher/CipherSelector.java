package com.aesgen.cipher;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class CipherSelector{
    public static CipherSchema select(String option) throws NoSuchSchemaException{
        switch(option){
            case "CBC": return new AESCBC();
            case "CTR": return new AESCTR();
            case "OFB": return new AESOFB();
            default: throw new NoSuchSchemaException("No such schema");
        }
    }
}
