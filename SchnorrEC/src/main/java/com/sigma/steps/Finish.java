package com.sigma.steps;

import com.Signable;
import com.Utils;
import com.Verifiable;
import com.communication.builder.BodyBuilder;
import com.communication.model.Certificate;
import com.communication.model.Signature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import com.schnorr.Verifier;
import com.sigma.Sendable;
import com.sigma.Sender;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;

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
    private BigInteger K1;
    @JsonIgnore
    private BigInteger K0;

    @Getter
    @Setter
    private Certificate certa;
    @Getter
    @Setter
    private Signature signa;
    @Getter
    @Setter
    private String maca;
    @Getter
    @Setter
    private String session;



    public Finish(Response response, Generator gen, Initialization init,Signable signer) {

        ECPoint g_y = Utils.getECPoint(response.getEphy(),gen);
        ECPoint K = gen.getECPoint(init.getX(),g_y);
        K1 = new BigInteger(1,Utils.KDF(K,1));
        K0 = new BigInteger(1,Utils.KDF(K,0));

        SigmaVerificator.sessionVerify(init.getSession(),response.getSession());
        SigmaVerificator.signVerify(gen, response.getCertb(), response.getSignb(), init, response);
        SigmaVerificator.macVerify(init.getSession(), gen, "01", response.getCertb(), K1, response.getMacb());


        certa = getCert(gen);
        signa = getSign(PREFIX, init, signer, response);

        maca = getMac(PREFIX, K1, init, gen);
        session = response.getSession();
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

    private Signature getSign(String prefix, Initialization init, Signable signer, Response response) {
        String payload = prefix + init.getSession()  + response.getEphy()+ init.getEphx();
        return signer.sign(payload);
    }

    private String getMac(String prefix, BigInteger X, Initialization init, Generator gen) {
        String message = prefix + init.getSession() + new BigInteger(Sender.send(gen, certa, false).getBytes()).toString(16);
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
}
