package com.sigma.steps;

import com.Signable;
import com.Utils;
import com.Verifiable;
import com.communication.builder.BodyBuilder;
import com.communication.model.Certificate;
import com.communication.model.RSignature;
import com.communication.model.Signature;
import com.communication.model.SimpleSignature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.io.BaseEncoding;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import com.schnorr.Verifier;
import com.sigma.Sendable;
import com.sigma.Sender;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;

import java.math.BigInteger;

/**
 * Created by kepuss on 05/01/16.
 */

public class Finish implements Sendable{

    @JsonIgnore
    final static String KEY_HOLDER = "MICHALK";
    @JsonIgnore
    final static String CA_NAME = "DUMMYNAME";
    @JsonIgnore
    final static String PREFIX = "00";

    @JsonIgnore
    private byte[] K1;
    @JsonIgnore
    private byte[] K0;

    @Getter
    @Setter
    private Certificate certa;

    private Signature signa;
    @Getter
    @Setter
    private String maca;
    @Getter
    @Setter
    private String session;

    public Finish() {
    }

    public Finish(Verifiable verifier,Response response, Generator gen, Initialization init,Signable signer) {

        ECPoint g_y = Utils.getECPoint(response.getEphy(),gen);
        ECPoint K = gen.getECPoint(init.getX(),g_y);
        K1 = Utils.KDF(K,"01");
        K0 = Utils.KDF(K,"00");

        SigmaVerificator.sessionVerify(init.getSession(),response.getSession());
        SigmaVerificator.signVerify(verifier, gen, response.getCertb(), response.getSignb(), init, response);
        SigmaVerificator.macVerify(init.getSession(), gen, "01", response.getCertb(), K1, response.getMacb());


        certa = getCert(gen);
        signa = getSign(PREFIX, init, signer, response);

        maca = getMac(PREFIX, K1, init, gen);
        session = response.getSession();
    }



    private Certificate getCert(Generator gen) {
        Certificate certificate = new Certificate();
        //certificate.setBody(BodyBuilder.getBody(KEY_HOLDER, gen.getPk(), gen.getCurveName(), gen.getHashName(), CA_NAME));
        certificate.setBody(BodyBuilder.getBody(KEY_HOLDER, gen.getPk(), gen.getCurveName(), gen.getHashName().replace("-",""), CA_NAME));
        Signature sign = new Signature();
        sign.setE(gen.getRandomBigInt().toString(16));
        sign.setS(gen.getRandomBigInt().toString(16));
        certificate.setSignature(sign);
        return certificate;
    }

    private Signature getSign(String prefix, Initialization init, Signable signer, Response response) {
        String payload = prefix + init.getSession()  + response.getEphy()+ init.getEphx();
        return signer.sign(payload);
    }

    private String getMac(String prefix, byte[] X, Initialization init, Generator gen) {
        byte[] message = BaseEncoding.base16().decode(prefix + init.getSession());
        byte[] payload = Arrays.concatenate(message, Sender.send(gen, certa, false).getBytes());
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
    public Signature getSigna() {
        return signa;
    }
    @JsonProperty(value = "signa")
    public Sendable getSignaSelect(){

            if(signa.getE() ==null){
                return new RSignature(signa);
            }else{
                return new SimpleSignature(signa);
            }

    }
    @JsonProperty(value = "signa")
    public void setSigna(RSignature signa) {

            this.signa = signa.getSignature();

    }

//    @JsonProperty(value = "signa")
//    public void setSigna(String signa) {
//
//        if(signa.contains("R_A")){
//            this.signa = Utils.mapObject(RSignature.class,signa).getSignature();
//        }else{
//            this.signa = Utils.mapObject(SimpleSignature.class,signa).getSignature();
//        }
//        //this.signa = signa;
//    }
}
