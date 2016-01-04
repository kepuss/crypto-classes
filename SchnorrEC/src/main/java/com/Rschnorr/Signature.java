package com.Rschnorr;

import com.schnorr.Generator;
import com.schnorr.PublicKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by kepuss on 20.12.15.
 */
public class Signature {
    private String message;
    private List<ECPoint> R;
    private List<BigInteger> h;
    private BigInteger sigma;

    public Signature(String message, List<ECPoint> r, List<BigInteger> h, BigInteger sigma) {
        this.message = message;
        R = r;
        this.h = h;
        this.sigma = sigma;
    }

    public BigInteger getSigma() {
        return sigma;
    }

    public void setSigma(BigInteger sigma) {
        this.sigma = sigma;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ECPoint> getR() {
        return R;
    }

    public void setR(List<ECPoint> r) {
        R = r;
    }

    public List<BigInteger> getH() {
        return h;
    }

    public void setH(List<BigInteger> h) {
        this.h = h;
    }
}
