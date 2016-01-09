package com.Services;

import com.Utils;
import com.google.common.io.BaseEncoding;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import com.schnorr.Signer;
import com.schnorr.Verifier;
import com.sigma.Sender;
import com.sigma.steps.Finish;
import com.sigma.steps.Initialization;
import com.sigma.steps.Response;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by kepuss on 09.01.16.
 */
public class SigmaSchnorrA {
    public static void sigmaA(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");
        String session = "75C014CD16F008664E60CE93E187043D";
        BigInteger X = new BigInteger("73246574710268087090324911967279444333339317441888111830190771098948520286872");

        // SIDE A
        Initialization init = new Initialization(gen,X,session);
        System.out.println(Sender.send(gen, init, false));


        Scanner reader = new Scanner(System.in);
        String responseJson = reader.nextLine();
        Response response = Utils.mapObject(Response.class, responseJson);

        // SIDE A

        Verifier verifier = new Verifier(new PublicKey( Utils.getECPoint(response.getCertb().getBody().getPub_key(), gen).normalize()),
                response.getSignb(),
                gen);
        Signer signer = new Signer(gen);

        Finish last = new Finish(verifier,response,gen,init,signer);
        System.out.println(Sender.send(gen, last,false));
        System.out.println("Side A key:" + BaseEncoding.base16().encode(last.getK0()));
    }
}
