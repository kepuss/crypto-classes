package com.Services;

import com.Utils;
import com.google.common.io.BaseEncoding;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import com.schnorr.Signer;
import com.schnorr.Verifier;
import com.sigma.Sender;
import com.sigma.steps.Final;
import com.sigma.steps.Finish;
import com.sigma.steps.Initialization;
import com.sigma.steps.Response;

import java.util.Scanner;

/**
 * Created by kepuss on 09.01.16.
 */
public class SigmaSchnorrB {
    public static void sigmaB(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");

        Scanner reader = new Scanner(System.in);
        String initJson = reader.nextLine();
        Initialization init= Utils.mapObject(Initialization.class, initJson);

        // SIDE B
        Signer signer = new Signer(gen);
        Response response = new Response(gen,signer,init);
        System.out.println(Sender.send(gen, response, false));


        String finishJson = reader.nextLine();
        Finish last= Utils.mapObject(Finish.class, finishJson);

        // SIDE B
        Verifier verifierB = new Verifier(new PublicKey( Utils.getECPoint(last.getCerta().getBody().getPub_key(), gen).normalize()),
                last.getSigna(),
                gen);
        Final fin = new Final(verifierB, response,last,gen,init);
        System.out.println("Side B key:" + BaseEncoding.base16().encode(fin.getK0()));
    }
}
