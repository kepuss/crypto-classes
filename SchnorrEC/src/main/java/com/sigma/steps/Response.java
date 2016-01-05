package com.sigma.steps;

import com.Signable;

import com.communication.builder.BodyBuilder;
import com.communication.builder.SignatureBuilder;
import com.communication.model.Certificate;
import com.communication.model.Signature;
import com.schnorr.Generator;
import com.sigma.Sendable;
import com.sigma.Sender;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kepuss on 04.01.16.
 */
@Getter
@Setter
public class Response implements Sendable {
    private String ephy;
    private Certificate certb;
    private Signature signb;
    private String macb;
    private String session;

    final static String KEY_HOLDER = "MICHALK";
    final static String CA_NAME = "DUMMYNAME";
    final static BigInteger OPAD = new BigInteger("47474747474747474747474747474747", 16);
    final static BigInteger IPAD = new BigInteger("74747474747474747474747474747474", 16);

    public Response(Generator gen, Signable signer, Initialization init) {
        //session = gen.getRandomBigInt().toString(16);
        session = init.getSession();

        BigInteger Y = gen.getRandomBigInt();
        ECPoint g_Y = gen.getECPoint(Y, gen.getG());

        ECPoint g_X = getInitPoint(init, gen);
        ECPoint K = gen.getECPoint(Y, g_X);


        ephy = ecToString(g_Y);
        certb = getCert(gen);
        signb = getSign(init, signer);

        BigInteger K0 = new BigInteger(KDF(K,0));

        macb = getMac(K0,init,gen);
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

    private Signature getSign(Initialization init, Signable signer) {
        String payload = "00" + init.getSession() + ephy + init.getEphx();
        return signer.sign(payload);
    }

    private String getMac(BigInteger X, Initialization init, Generator gen) {
        String message = "00" + init.getSession() + new BigInteger(Sender.send(gen, certb, false).getBytes()).toString(16);
        return new BigInteger(computeMac(message.getBytes(),X)).toString(16);

    }

    private byte[] computeMac(byte[] message, BigInteger key) {
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger xorOpad = key.xor(OPAD);
        BigInteger xorIpad = key.xor(IPAD);

        byte[] ipadMessage = Arrays.concatenate(xorIpad.toByteArray(), message);

        byte[] firstHash = hash.digest(ipadMessage);

        byte[] finalHashInput = Arrays.concatenate(xorOpad.toByteArray(), firstHash);

        return hash.digest(finalHashInput);

    }

    private ECPoint getInitPoint(Initialization init, Generator gen) {
        String pk = init.getEphx().substring(2);
        String pkX = pk.substring(0, pk.length() / 2);
        String pkY = pk.substring((pk.length() / 2), pk.length());
        return gen.getCurve().getCurve().createPoint(new BigInteger(pkX, 16), new BigInteger(pkY, 16));
    }

    private byte[] KDF(ECPoint K,int i){
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] input = Arrays.append(ecToString(K).getBytes(),(byte)i);
        byte[] output = hash.digest(input);

        return Arrays.copyOf(output,128);
    }

    private String ecToString(ECPoint p){
        return "04"+new BigInteger(p.normalize().getAffineXCoord().getEncoded()).toString(16)+new BigInteger(p.normalize().getAffineYCoord().getEncoded()).toString(16);
    }


}
