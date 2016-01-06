package com.communication.model;

import com.sigma.Sendable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kepuss on 06.01.16.
 */
@Getter
@Setter
public class SimpleSignature implements Sendable {
    private String e;
    private String s;

    public SimpleSignature(Signature signature){
        this.e = signature.getE();
        this.s = signature.getS();
    }
}
