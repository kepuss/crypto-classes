package com.rsa;

import com.rsa.reader.FileReader;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kepuss on 27.04.15.
 */
public class RSACBC {
    //Length 192bits
    String IV = "6bc1bee22e409f96e93d7e117393172abee22e409f96e93d";
    byte[] IVbyte = DatatypeConverter.parseHexBinary(IV);

    public void run(){
        List<byte[]> allMessages = FileReader.readBytesLinesFromFile("input/messages10");
        RSAScheme rsa = new RSAScheme(2);
        long startTime = System.nanoTime();

        for(byte[] oneMessage : allMessages){
            List<byte[]> messageParted = partMessage(oneMessage);
            CBCBlock(messageParted,rsa);
        }
        long endTime = System.nanoTime();
        System.out.println(" time: "+(endTime - startTime));
    }

    private void CBCBlock(List<byte[]> messages, RSAScheme rsa){

        for(byte[] message : messages){
            byte[] xorMessage = new byte[message.length];
            for(int i =0; i<xorMessage.length; i++){
                xorMessage[i] = (byte)(IVbyte[i]^message[i]);
            }
            BigInteger cipher = rsa.encrypt(new BigInteger(xorMessage));
            IVbyte = Arrays.copyOf(cipher.toByteArray(),24);
        }
    }

    private List<byte[]> partMessage(byte[] message){
        List<byte[]> messageBlocks = new ArrayList<>();
        int i =24;
        for(; i<message.length;i+=24){
            messageBlocks.add(Arrays.copyOfRange(message,i-24,i));
        }
        messageBlocks.add(Arrays.copyOfRange(message,i-24,message.length));
        return messageBlocks;
    }
}
