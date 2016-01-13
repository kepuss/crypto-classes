package com.communication.model;

import com.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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

@JsonPropertyOrder({"R_A","R_B","h_A","h_B","sigma"})
public class RSignature implements Sendable {
    @JsonProperty(value = "R_A")
    private String R_A;
    @JsonProperty(value = "R_B")
    private String R_B;
    @Getter
    @Setter
    private String h_A;
    @Getter
    @Setter
    private String h_B;
    @Getter
    @Setter
    private String sigma;

    public RSignature() {
    }

    public RSignature(Signature signature) {
        R_A = Utils.ecToString(signature.getR().get(0)).toUpperCase();
        R_B = Utils.ecToString(signature.getR().get(1)).toUpperCase();

        h_A = signature.getH().get(0).toString(16).toUpperCase();
        h_B = signature.getH().get(1).toString(16).toUpperCase();

        sigma = signature.getSigma().toString(16).toUpperCase();
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
    @JsonProperty(value = "R_A")
    public String getR_A() {
        return R_A;
    }
    @JsonProperty(value = "R_A")
    public void setR_A(String r_A) {
        R_A = r_A;
    }

    @JsonProperty(value = "R_B")
    public String getR_B() {
        return R_B;
    }
    @JsonProperty(value = "R_B")
    public void setR_B(String r_B) {
        R_B = r_B;
    }
}
