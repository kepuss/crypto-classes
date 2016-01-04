package com.schnorr;

import org.bouncycastle.math.ec.ECPoint;

/**
 * Created by kepuss on 27.10.15.
 */
public class PublicKey {
    private ECPoint P;
 //   private ECPoint Q;


    public PublicKey() {
    }

    public PublicKey(ECPoint p) {
        P = p;
    }

    public ECPoint getP() {
        return P;
    }

    public void setP(ECPoint p) {
        P = p;
    }

////    public ECPoint getQ() {
//        return Q;
//    }
//
// //   public void setQ(ECPoint q) {
//        Q = q;
//    }
}
