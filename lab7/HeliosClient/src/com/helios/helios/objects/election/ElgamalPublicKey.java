package com.helios.helios.objects.election;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigInteger;

/**
 * Created by kepuss on 16.05.15.
 */

public class ElgamalPublicKey {
    private String g;
    private String p;
    private String q;
    private String y;

    public String getG() {
        return g;
    }

    @JsonIgnore
    public BigInteger getGBigInt() {
        return new BigInteger(g);
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getP() {
        return p;
    }
    @JsonIgnore
    public BigInteger getPBigInt() {
        return new BigInteger(p);
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getQ() {
        return q;
    }

    @JsonIgnore
    public BigInteger getQBigInt() {
        return new BigInteger(q);
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getY() {
        return y;
    }
    @JsonIgnore
    public BigInteger getYBigInt() {
        return new BigInteger(y);
    }

    public void setY(String y) {
        this.y = y;
    }
}
