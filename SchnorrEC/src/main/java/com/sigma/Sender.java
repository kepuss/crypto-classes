package com.sigma;

import com.communication.builder.BodyBuilder;
import com.communication.builder.SignatureBuilder;
import com.communication.model.Certificate;
import com.communication.model.RSignature;
import com.communication.model.Signature;
import com.communication.model.SimpleSignature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.schnorr.Generator;

/**
 * Created by kepuss on 04.01.16.
 */
public class Sender {
    public static String send(Generator gen, Sendable message, boolean wrap){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, wrap);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
       // mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String result=null;
        try {
            result =mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
