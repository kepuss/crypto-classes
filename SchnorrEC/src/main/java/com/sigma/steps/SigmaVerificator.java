package com.sigma.steps;

import com.Utils;
import com.communication.model.Certificate;
import com.communication.model.Signature;
import com.schnorr.Generator;
import com.schnorr.PublicKey;
import com.schnorr.Verifier;
import com.sigma.Sender;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by kepuss on 05.01.16.
 */
public class SigmaVerificator {

    public static void sessionVerify(String session1, String session2){
        System.out.println("Session verification result: "+session1.equals(session2));
    }

    public static void signVerify(Generator gen, Certificate cert,Signature sign, Initialization init, Response response){
        Verifier verifier = new Verifier(new PublicKey(getEcPoint(cert.getBody().getPub_key(),gen).normalize()),
                sign,
                gen);
        System.out.println("Signature verification result: "+verify01(verifier, init, response.getEphy()));
    }

    public static void signVerify00(Generator gen, Certificate cert,Signature sign,  Initialization init, Response response){
        Verifier verifier = new Verifier(new PublicKey(getEcPoint(cert.getBody().getPub_key(),gen).normalize()),
                sign,
                gen);
        System.out.println("Signature verification result: "+verify00(verifier, init, response));
    }

    public static void macVerify(String session, Generator gen,String prefix, Certificate cert, BigInteger K1, String mac){


        //ECPoint g_y = Utils.getECPoint(response.getEphy(),gen);
        //ECPoint K = gen.getECPoint(init.getX(),g_y);
//        byte[] K1 = Utils.KDF(K,1);

        System.out.println("Mac verification result: "+isMacCorrect(K1, session, gen, cert, mac, prefix));
    }

    private static ECPoint getEcPoint(String point, Generator gen) {
        String pk = point.substring(2);
        String pkX = pk.substring(0, pk.length() / 2);
        String pkY = pk.substring((pk.length() / 2), pk.length());
        return gen.getCurve().getCurve().createPoint(new BigInteger(pkX, 16), new BigInteger(pkY, 16));
    }

    private static boolean verify01(Verifier verifier, Initialization init, String ephy){
        String payload = "01" + init.getSession()  + init.getEphx()+ ephy;
        return verifier.verify(payload);
    }

    private static boolean verify00(Verifier verifier, Initialization init,Response response){
        String payload = "00" + init.getSession() + response.getEphy() + init.getEphx();
        return verifier.verify(payload);
    }

    private  static boolean isMacCorrect(BigInteger K1,String session, Generator gen, Certificate cert, String mac , String prefix){
        String calculatedMac = getMac(prefix, K1, session, gen, cert);
        if(calculatedMac.equals(mac)){
            return true;
        }else{
            return false;
        }
    }

    private static String getMac(String prefix, BigInteger X, String session, Generator gen, Certificate certb) {
        String message = prefix + session + new BigInteger(Sender.send(gen, certb, false).getBytes()).toString(16);
        return new BigInteger(1, Utils.computeMac(message.getBytes(), X)).toString(16);

    }
}
