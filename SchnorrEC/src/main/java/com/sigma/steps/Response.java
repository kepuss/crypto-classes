package com.sigma.steps;

import com.Signable;

import com.communication.builder.BodyBuilder;
import com.communication.builder.SignatureBuilder;
import com.communication.model.Certificate;
import com.communication.model.Signature;
import com.schnorr.Generator;
import com.sigma.Sendable;
import com.sigma.Sender;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kepuss on 04.01.16.
 */
public class Response implements Sendable {
    private String ephy;
    private Certificate certb;
    private Signature signb;
    private String macb;
    private String session;

    final static String KEY_HOLDER="MICHALK";
    final static String CA_NAME="DUMMYNAME";
    final static byte[] OPAD = new BigInteger("47474747474747474747474747474747",16).toByteArray();
    final static byte[] IPAD = new BigInteger("74747474747474747474747474747474",16).toByteArray();

    public Response(Generator gen, Signable signer, Initialization init) {
        session = gen.getRandomBigInt().toString(16);

        BigInteger Y = gen.getRandomBigInt();
        ECPoint randomPoint = gen.getECPoint(Y,gen.getG());

        ephy = "04"+randomPoint.normalize().getRawXCoord().toBigInteger().toString(16)+randomPoint.normalize().getRawYCoord().toBigInteger().toString(16);
        certb = getCert(gen);
        signb = getSign(init,signer);
    }

    private Certificate getCert(Generator gen){
        Certificate certificate = new Certificate();
        certificate.setBody(BodyBuilder.getBody(KEY_HOLDER, gen.getPk(), gen.getCurveName(), gen.getHashName(), CA_NAME));
        Signature sign = new Signature();
        sign.setE(gen.getRandomBigInt().toString(16));
        sign.setS(gen.getRandomBigInt().toString(16));
        certificate.setSignature(sign);
        return certificate;
    }

    private Signature getSign(Initialization init,Signable signer){
        String payload= "00"+init.getSession()+ephy+init.getEphx();
        return signer.sign(payload);
    }

    private String getMac(BigInteger X,Initialization init, Generator gen){
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] K = hash.digest(Arrays.append(X.toByteArray(),(byte)0));
        K = Arrays.copyOf(K,128);

        String message = "00"+init.getSession()+ new BigInteger(Sender.send(gen, certb, false).getBytes()).toString(16);
    }

    private byte[] computeMac(byte[] input, byte[] key){

    }

}
