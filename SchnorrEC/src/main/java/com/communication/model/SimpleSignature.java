package com.communication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public SimpleSignature() {
    }

    public SimpleSignature(Signature signature){
        this.e = signature.getE().toUpperCase();
        this.s = signature.getS().toUpperCase();
    }

    @JsonIgnore
    public Signature getSignature(){
        Signature signature = new Signature();
        signature.setE(e.toUpperCase());
        signature.setS(s.toUpperCase());
        return signature;
    }
}
