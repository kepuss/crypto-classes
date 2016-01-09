package com.communication.model;

import com.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schnorr.Generator;
import com.sigma.Sendable;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kepuss on 06.01.16.
 */
@Getter
@Setter
public class RSignature implements Sendable {
    private String R_A;
    private String R_B;
    private String h_A;
    private String h_B;
    private String sigma;

    public RSignature(Signature signature) {
        R_A = Utils.ecToString(signature.getR().get(0));
        R_B = Utils.ecToString(signature.getR().get(1));

        h_A = signature.getH().get(0).toString(16);
        h_B = signature.getH().get(1).toString(16);

        sigma = signature.getSigma().toString(16);
    }

    @JsonIgnore
    public Signature getSignature(){

        Generator gen = new Generator("brainpoolP256r1","SHA-256");
        Signature signature = new Signature();
        List<ECPoint> R = new ArrayList<ECPoint>();
        R.add(Utils.getECPoint(R_A,gen));
        R.add(Utils.getECPoint(R_B, gen));
        signature.setR(R);

        List<BigInteger> H = new ArrayList<BigInteger>();
        H.add(new BigInteger(h_A,16));
        H.add(new BigInteger(h_B, 16));
        signature.setH(H);

        signature.setSigma(new BigInteger(sigma,16));
        return signature;

    }
}
