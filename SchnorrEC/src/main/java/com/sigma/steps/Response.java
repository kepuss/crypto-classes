package com.sigma.steps;

import com.Signable;

import com.Utils;
import com.communication.builder.BodyBuilder;
import com.communication.model.Certificate;
import com.communication.model.RSignature;
import com.communication.model.Signature;
import com.communication.model.SimpleSignature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schnorr.Generator;
import com.sigma.Sendable;
import com.sigma.Sender;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by kepuss on 04.01.16.
 */

public class Response implements Sendable {
    @JsonIgnore
    private BigInteger K1;
    @JsonIgnore
    private BigInteger K0;

    @Getter
    @Setter
    private String ephy;
    @Getter
    @Setter
    private Certificate certb;

    private Signature signb;
    @Getter
    @Setter
    private String macb;
    @Getter
    @Setter
    private String session;

    @JsonIgnore
    final static String KEY_HOLDER = "MICHALK";
    @JsonIgnore
    final static String CA_NAME = "DUMMYNAME";
    @JsonIgnore
    final static String PREFIX = "01";


    public Response(Generator gen, Signable signer, Initialization init) {
        //session = gen.getRandomBigInt().toString(16);
        session = init.getSession();

        BigInteger Y = gen.getRandomBigInt();
        ECPoint g_Y = gen.getECPoint(Y, gen.getG());

        ECPoint g_X = Utils.getECPoint(init.getEphx(), gen);
        ECPoint K = gen.getECPoint(Y, g_X);


        ephy = Utils.ecToString(g_Y);
        certb = getCert(gen);
        signb = getSign(PREFIX, init, signer);

         K1 = new BigInteger(1,Utils.KDF(K, 1));
         K0 = new BigInteger(1,Utils.KDF(K, 0));


        macb = getMac(PREFIX, K1,init,gen);
    }

    private Certificate getCert(Generator gen) {
        Certificate certificate = new Certificate();
        certificate.setBody(BodyBuilder.getBody(KEY_HOLDER, gen.getPk(), gen.getCurveName(), gen.getHashName(), CA_NAME));
        Signature sign = new Signature();
        sign.setE(gen.getRandomBigInt().toString(16));
        sign.setS(gen.getRandomBigInt().toString(16));
        certificate.setSignature(sign);
        return certificate;
    }

    private Signature getSign(String prefix, Initialization init, Signable signer) {
        String payload = prefix + init.getSession()  + init.getEphx()+ ephy;
        return signer.sign(payload);
    }

    private String getMac(String prefix, BigInteger X, Initialization init, Generator gen) {
        String message = prefix + init.getSession() + new BigInteger(Sender.send(gen, certb, false).getBytes()).toString(16);
        return new BigInteger(1,Utils.computeMac(message.getBytes(), X)).toString(16);

    }

    @JsonIgnore
    public BigInteger getK1() {
        return K1;
    }
    @JsonIgnore
    public BigInteger getK0() {
        return K0;
    }

    @JsonIgnore
    public Signature getSignb() {
        return signb;
    }

    @JsonProperty(value = "signb")
    public Sendable getSignbSelect(){

        if(signb.getE() ==null){
            return new RSignature(signb);
        }else{
            return new SimpleSignature(signb);
        }

    }

    public void setSignb(Signature signb) {
        this.signb = signb;
    }
}
