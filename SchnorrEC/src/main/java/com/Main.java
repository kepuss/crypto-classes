package com;


import com.Services.SigmaSchnorrA;
import com.Services.SigmaSchnorrB;
import com.communication.Receiver;
import com.communication.Sender;
import com.schnorr.Generator;
import com.schnorr.Signature;
import com.schnorr.Signer;
import com.schnorr.Verifier;

public class Main {

    public static void main(String[] args) {
//        Generator gen = new Generator("P-256","SHA-256");
//
//
//        Signer signer = new Signer(gen);
//        Signature signature = signer.signSig("Message");
//
//        String message = Sender.send(gen, signature);
//        System.out.println(message);
//        Receiver receiver = new Receiver(message);
//        Generator rGen = receiver.getGen();
//        Signature eSign = receiver.getSign();
//
//        Verifier verifier = new Verifier(rGen.getPk(),eSign,rGen);
//        System.out.print(verifier.verify("Message"));

        for(String arg : args){
            switch(arg.charAt(0)){
                case '1':
                    SigmaSchnorrA.sigmaA(); break;
                case '2':
                    SigmaSchnorrB.sigmaB(); break;
            }
        }




    }
}
