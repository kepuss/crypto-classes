package com.communication.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by kepuss on 29.11.15.
 */
@JsonRootName(value = "SIG")
@Getter
@Setter
public class Signature {
    private String e;
    private String s;

    private String message;
    private List<ECPoint> R;
    private List<BigInteger> h;
    private BigInteger sigma;

    public  Signature(){}

    public Signature(String message, List<ECPoint> r, List<BigInteger> h, BigInteger sigma) {
        this.message = message;
        R = r;
        this.h = h;
        this.sigma = sigma;
    }
}
