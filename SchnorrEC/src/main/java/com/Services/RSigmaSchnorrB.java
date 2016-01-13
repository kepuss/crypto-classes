package com.Services;

import com.Rschnorr.Ring;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by kepuss on 11/01/16.
 */
public class RSigmaSchnorrB {
    public static void sigmaB(){
        Generator gen = new Generator("brainpoolP256r1","SHA-256");

        Scanner reader = new Scanner(System.in);
        String initJson = reader.nextLine();
        Initialization init= Utils.mapObject(Initialization.class, initJson);

        // SIDE B
        Signer signer = new Signer(gen);
        //Response response = new Response(gen,signer,init,Utils.getECPoint("045B32D9FE1418D79EC0EB4FE73E7BBD4A499B5B816DBEDFF0E558E4EF1F7B56BD810655A10F97EB078D9C650E00EFEF84C6931DD342AC52750843587FC88E7649",gen));
        Response response = new Response(gen,signer,init);
        System.out.println(Sender.send(gen, response, false));


        String finishJson = reader.nextLine();
        Finish last= Utils.mapObject(Finish.class, finishJson);

        // SIDE B
        List<PublicKey> pks = new ArrayList<PublicKey>();
        PublicKey pk0 = new PublicKey();
        pk0.setP(Utils.getECPoint(last.getCerta().getBody().getPub_key(),gen));
        PublicKey pk1 = new PublicKey();
        //pk1.setP(gen.getPk());
        pks.add(pk0);
        pks.add(gen.getPk());


        Ring ring = new Ring(1,gen,pks,pks);
        com.Rschnorr.Verifier Rverifier = new com.Rschnorr.Verifier(gen,ring,last.getSigna());
        Final fin = new Final(Rverifier, response,last,gen,init);
        System.out.println("Side B key:" + BaseEncoding.base16().encode(fin.getK0()));
    }
}
