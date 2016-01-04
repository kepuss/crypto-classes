package com.schnorr;

import java.math.BigInteger;

/**
 * Created by kepuss on 27.10.15.
 */
public class Signature {
    private BigInteger R;
    private BigInteger s;

    public BigInteger getR() {
        return R;
    }

    public void setR(BigInteger r) {
        R = r;
    }

    public BigInteger getS() {
        return s;
    }

    public void setS(BigInteger s) {
        this.s = s;
    }
}
