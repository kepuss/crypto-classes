package com.helios.helios.objects.vote;

import com.helios.helios.objects.election.Election;
import com.helios.service.Crypto;
import com.helios.service.HttpJsonReader;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kepuss on 16.05.15.
 */

public class Vote {
    private List<EncryptedAnswer> answers;
    private String election_hash;
    private String election_uuid;

    public Vote(Election el) throws IOException, NoSuchAlgorithmException {
        answers = new ArrayList<>();
        EncryptedAnswer enan = new EncryptedAnswer(el,1);
        answers.add(enan);
        HttpJsonReader<Election> reader = new HttpJsonReader<>();
        election_hash = Crypto.sha256AndBase64MinusOne(reader.toJson(el));
        election_uuid = el.getUuid();
    }

    public List<EncryptedAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<EncryptedAnswer> answers) {
        this.answers = answers;
    }

    public String getElection_hash() {
        return election_hash;
    }

    public void setElection_hash(String election_hash) {
        this.election_hash = election_hash;
    }

    public String getElection_uuid() {
        return election_uuid;
    }

    public void setElection_uuid(String election_uuid) {
        this.election_uuid = election_uuid;
    }

    public void createVote(Election el) throws IOException, NoSuchAlgorithmException {
        election_uuid = el.getUuid();
        HttpJsonReader<Election> reader = new HttpJsonReader<>();
        election_hash = Crypto.sha256AndBase64(reader.toJson(el));
        election_hash = election_hash.substring(0, election_hash.length() - 1);



    }
}
