package com.sigma.steps;

import com.schnorr.Generator;
import com.sigma.Sendable;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by kepuss on 04.01.16.
 */
@Getter
@Setter
public class Initialization implements Sendable {
    private BigInteger X;
    private String ephx;
    private String session;

    public Initialization(Generator gen) {
        session = gen.getRandomBigInt().toString(16);
        ECPoint randomPoint = gen.getRandomECPoint();
        ephx = "04"+randomPoint.normalize().getRawXCoord().toBigInteger().toString(16)+randomPoint.normalize().getRawYCoord().toBigInteger().toString(16);
    }
}
