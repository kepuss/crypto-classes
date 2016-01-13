package com.Services;

import com.Rschnorr.Ring;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by kepuss on 13/01/16.
 */
public class RSigmaSchnorrA {
    public static void sigmaA() {
        Generator gen = new Generator("brainpoolP256r1", "SHA-256");
        Scanner reader = new Scanner(System.in);
        // SIDE A
        Initialization init = new Initialization(gen);
        System.out.println(Sender.send(gen, init, false));

        // SIDE B
//        Signer signer = new Signer(gen);
//        Response response = new Response(gen, signer, init);
//        System.out.println(Sender.send(gen, response, false));

        String responseJson = reader.nextLine();
        Response response = Utils.mapObject(Response.class, responseJson);

        // SIDE A
        List<PublicKey> pklist = new ArrayList<PublicKey>();
        pklist.add(new PublicKey(Utils.getECPoint(response.getCertb().getBody().getPub_key(), gen)));
        Ring ring = new Ring(1, gen, pklist);
        com.Rschnorr.Signer Rsigner = new com.Rschnorr.Signer(ring, gen);

        Verifier verifier = new Verifier(new PublicKey(Utils.getECPoint(response.getCertb().getBody().getPub_key(), gen).normalize()),
                response.getSignb(),
                gen);

        Finish last = new Finish(verifier, response, gen, init, Rsigner);
        System.out.println(Sender.send(gen, last, false));
        System.out.println("Side A key:" + BaseEncoding.base16().encode(last.getK0()));
    }
}
