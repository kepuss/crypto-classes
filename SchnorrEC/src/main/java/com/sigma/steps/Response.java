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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.io.BaseEncoding;
import com.schnorr.Generator;
import com.sigma.Sendable;
import com.sigma.Sender;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;

import java.math.BigInteger;

/**
 * Created by kepuss on 04.01.16.
 */

@JsonPropertyOrder({"ephy", "certb", "signb", "macb", "session"})
public class Response implements Sendable {
    @JsonIgnore
    private byte[] K1;
    @JsonIgnore
    private  byte[] K0;

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

    public Response() {
    }

    public Response(Generator gen, Signable signer, Initialization init) {
        //session = gen.getRandomBigInt().toString(16);
        session = init.getSession();

        BigInteger Y = gen.getRandomBigInt();
        ECPoint g_Y = gen.getECPoint(Y, gen.getG());

        ECPoint g_X = Utils.getECPoint(init.getEphx(), gen);
        ECPoint K = gen.getECPoint(Y, g_X);


        ephy = Utils.ecToString(g_Y).toUpperCase();
        certb = getCert(gen);
        signb = getSign(PREFIX, init, signer);

         K1 = Utils.KDF(K, "01");
         K0 = Utils.KDF(K, "00");


        macb = getMac(PREFIX, K1,init,gen);
    }

    public Response(Generator gen, Signable signer, Initialization init, ECPoint g_Y) {
        //session = gen.getRandomBigInt().toString(16);
        session = init.getSession();

        BigInteger Y = gen.getRandomBigInt();
        //ECPoint g_Y = gen.getECPoint(Y, gen.getG());

        ECPoint g_X = Utils.getECPoint(init.getEphx(), gen);
        ECPoint K = gen.getECPoint(Y, g_X);


        ephy = Utils.ecToString(g_Y).toUpperCase();
        certb = getCert(gen);
        signb = getSign(PREFIX, init, signer);

        K1 = Utils.KDF(K, "01");
        K0 = Utils.KDF(K, "00");


        macb = getMac(PREFIX, K1,init,gen);
    }

    public Response(Generator gen, Signable signer, Initialization init, BigInteger Y, Certificate cert) {
        //session = gen.getRandomBigInt().toString(16);
        session = init.getSession();

        //BigInteger Y = gen.getRandomBigInt();
        ECPoint g_Y = gen.getECPoint(Y, gen.getG());

        ECPoint g_X = Utils.getECPoint(init.getEphx(), gen);
        ECPoint K = gen.getECPoint(Y, g_X.normalize());
        //System.out.println("K "+Utils.ecToString(K));


        ephy = Utils.ecToString(g_Y).toUpperCase();
        certb = cert;
        signb = getSign(PREFIX, init, signer);

        K1 = Utils.KDF(K, "01");
        K0 = Utils.KDF(K, "00");

       // System.out.println("K1 "+K1.toString(16));
//        System.out.println("K0 "+K0.toString(16));

        macb = getMac(PREFIX, K1,init,gen);
    }

    private Certificate getCert(Generator gen) {
        Certificate certificate = new Certificate();
        certificate.setBody(BodyBuilder.getBody(KEY_HOLDER, gen.getPk(), gen.getCurveName(), gen.getHashName().replace("-",""), CA_NAME));
        Signature sign = new Signature();
        sign.setE(gen.getRandomBigInt().toString(16));
        sign.setS(gen.getRandomBigInt().toString(16));
        certificate.setSignature(sign);
        return certificate;
    }

    private Signature getSign(String prefix, Initialization init, Signable signer) {
        String payload = prefix + init.getSession()  + init.getEphx()+ ephy;
        System.out.println("payload "+payload);
        return signer.sign(payload);
    }

    private String getMac(String prefix, byte[] X, Initialization init, Generator gen) {
        byte[] message = BaseEncoding.base16().decode(prefix + init.getSession());
        byte[] payload = Arrays.concatenate(message,Sender.send(gen, certb, false).getBytes());
        return BaseEncoding.base16().encode(Utils.computeMac(payload, X));

    }

    @JsonIgnore
    public byte[] getK1() {
        return K1;
    }
    @JsonIgnore
    public byte[] getK0() {
        return K0;
    }

    @JsonIgnore
    public Signature getSignb() {
        return signb;
    }

    @JsonProperty(value = "signb")
    public Sendable getSignbSelect(){

        System.out.println(signb);
        if(signb.getE() ==null){
            return new RSignature(signb);
        }else{
            return new SimpleSignature(signb);
        }

    }
    @JsonProperty(value = "signb")
    public void setSignb(Signature signb) {
        this.signb = signb;
    }





}
