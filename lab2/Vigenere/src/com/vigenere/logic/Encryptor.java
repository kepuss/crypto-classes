package com.vigenere.logic;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/15/15.
 */
public class Encryptor{
    public String encrypt(String key, String message){
        char[] keyArray = key.toCharArray();
        char[] messageArray = message.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<message.length();i++){
            stringBuilder.append( (char)(((int)messageArray[i]) ^ ((int) keyArray[i%key.length()])) );
        }
        return stringBuilder.toString();
    }
}
