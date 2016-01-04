package com.schnorr;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by kepuss on 27.10.15.
 */
public class Generator {
    private PrivateKey sk = new PrivateKey();
    private PublicKey pk = new PublicKey();

    private ECPoint g;
    private BigInteger N;
    private  String hashName;
    private  String curveName;
    private X9ECParameters curve;



    public Generator(String curveName, String hashName) {
        this.hashName=hashName;
        this.curveName=curveName;

        curve = ECNamedCurveTable.getByName(curveName); // or whatever curve you want to us

        g = curve.getG();
        N = curve.getN();


        BigInteger a = getRandomBigInt();
        //2
        ECPoint P = getECPoint(a,g);
        //3


        //5
        pk.setP(P);
//        pk.setQ(getECPoint(a,P));

        sk.setA(a);
        sk.setPk(pk);
    }


    public  ECPoint getRandomECPoint(){
        int nBitLength = N.bitLength();
        BigInteger x;
        Random random = new Random();
        do
        {
            x = new BigInteger(nBitLength, random);
        }
        while (x.equals(BigInteger.ZERO)  || (x.compareTo(N) >= 0));
        ECPoint randomPoint = g.multiply(x);
        return randomPoint;
    }

    public ECPoint getECPoint(BigInteger a, ECPoint point) {
        ECPoint randomPoint = point.multiply(a).normalize();
        return randomPoint;
    }

    public  BigInteger getRandomBigInt(){
        int nBitLength = N.bitLength();
        BigInteger x;
        Random random = new Random();
        do
        {
            x = new BigInteger(nBitLength, random);
        }
        while (x.equals(BigInteger.ZERO)  || (x.compareTo(N) >= 0));
        return x;
    }

    public PrivateKey getSk() {
        return sk;
    }

    public PublicKey getPk() {
        return pk;
    }

    public ECPoint getG() {
        return g;
    }

    public BigInteger getN() {
        return N;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public X9ECParameters getCurve() {
        return curve;
    }

    public void setCurve(X9ECParameters curve) {
        this.curve = curve;
    }

    public void setPk(PublicKey pk) {
        this.pk = pk;
    }

    public String getCurveName() {
        return curveName;
    }

    public void setCurveName(String curveName) {
        this.curveName = curveName;
    }
}
