package com.communication;

import com.communication.builder.BodyBuilder;
import com.communication.builder.SignatureBuilder;
import com.communication.model.Certificate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.schnorr.Generator;
import com.schnorr.Signature;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;

/**
 * Created by kepuss on 29.11.15.
 */
public class Sender {
    final static String KEY_HOLDER="MICHALK";
    final static String CA_NAME="DUMMYNAME";



    public static String send(Generator gen, Signature signature){
        Certificate certificate = new Certificate();



        certificate.setBody(BodyBuilder.getBody(KEY_HOLDER, gen.getPk(), gen.getCurveName(), gen.getHashName(), CA_NAME));
        certificate.setSignature(SignatureBuilder.getSign(signature));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        //mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String result=null;
        try {
            result =mapper.writeValueAsString(certificate);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
