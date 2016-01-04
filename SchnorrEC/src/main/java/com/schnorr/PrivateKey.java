package com.schnorr;

import java.math.BigInteger;

/**
 * Created by kepuss on 27.10.15.
 */
public class PrivateKey {
    BigInteger a;
    PublicKey pk;

    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public PublicKey getPk() {
        return pk;
    }

    public void setPk(PublicKey pk) {
        this.pk = pk;
    }
}
