package com.communication;

import com.communication.builder.BodyBuilder;
import com.communication.builder.SignatureBuilder;
import com.communication.model.Certificate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.schnorr.Generator;
import com.schnorr.Signature;

import java.io.IOException;

/**
 * Created by kepuss on 29.11.15.
 */
public class Receiver {
    private Certificate cert;

    public Receiver(String source){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        mapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true);
        try {
            cert = mapper.readValue(source, Certificate.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Generator getGen(){
        return BodyBuilder.getGenerator(cert.getBody());
    }
    public Signature getSign(){
        return SignatureBuilder.getSchnorrSign(cert.getSignature());
    }



}
