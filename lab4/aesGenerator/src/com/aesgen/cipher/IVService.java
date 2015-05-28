package com.aesgen.cipher;

import com.aesgen.reader.FileReader;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class IVService{
    public byte[] IV;

    public IVService(){
        IV = new byte[16];
 //       Arrays.fill(IV, ( byte ) 0);
  //      IV[15] = (byte)1;
        IV = FileReader.readBytesFromFile("resources/currentIV");
    }

    public void incrementIV(){
        for(int i =IV.length-1;i>=0;i--){
            if(IV[i] != (byte)-1){
                IV[i]++; return;
            }else{
                IV[i]++;
            }
        }

    }

    public void saveIV(){
        FileReader.saveBytesToFile("resources/currentIV",IV);
    }

    public byte[] getIV (){
        return IV;
    }

    public void setIV ( byte[] IV ){
        this.IV = IV;
    }

    public void setIV ( String IV ){

        this.IV = DatatypeConverter.parseHexBinary(IV);
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte one : IV){
            String tmp = Integer.toBinaryString(256 + ( int ) one);
            stringBuilder.append(tmp.substring(tmp.length()-8)+" ");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
