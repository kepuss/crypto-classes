package com.helios;

import com.helios.helios.objects.election.Election;
import com.helios.helios.objects.verifier.Verifier;
import com.helios.helios.objects.vote.Challenge;
import com.helios.helios.objects.vote.EncryptedAnswer;
import com.helios.helios.objects.vote.Vote;
import com.helios.service.Crypto;
import com.helios.service.HttpJsonReader;
import com.helios.service.URLS;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
	// write your code here
        HttpJsonReader<Election> reader = new HttpJsonReader<>();
        Election el = reader.fromJson(URLS.ELECTION, Election.class);
//        Challenge answer = new Challenge(el);
//        HttpJsonReader<Challenge> readerAn = new HttpJsonReader<>();
//        System.out.println(readerAn.toJson(answer));
//        System.out.println(Crypto.sha256(readerAn.toJson(answer)));
        HttpJsonReader<Vote> answerReader = new HttpJsonReader<>();
        Vote answer = new Vote(el);
        Verifier ver = new Verifier(answer,el);

        System.out.println(answerReader.toJson(answer));

        System.out.println(ver.verify());

    }
}
