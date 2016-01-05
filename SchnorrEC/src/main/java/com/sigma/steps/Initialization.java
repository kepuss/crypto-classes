package com.sigma.steps;

import com.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schnorr.Generator;
import com.sigma.Sendable;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by kepuss on 04.01.16.
 */

public class Initialization implements Sendable {
    @JsonIgnore
    private BigInteger X;
    @Getter
    @Setter
    private String ephx;
    @Getter
    @Setter
    private String session;

    public Initialization(Generator gen) {
        X=gen.getRandomBigInt();
        session = gen.getRandomBigInt().toString(16);
        ECPoint randomPoint = gen.getECPoint(X, gen.getG());
        ephx = Utils.ecToString(randomPoint);
    }
    @JsonIgnore
    public BigInteger getX() {
        return X;
    }
}
