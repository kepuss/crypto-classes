package com.communication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnorr.PublicKey;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.ArrayList;
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

    @JsonIgnore
    public List<PublicKey> getPKs(){
        List<PublicKey> pks = new ArrayList<PublicKey>();
        //for(ECPoint point : R){
        for(int i=0;i<R.size();i++){
        //for(int i=1;i>=0;i--){
            PublicKey pk = new PublicKey();
            pk.setP(R.get(i));
            pks.add(pk);
        }
        return pks;
    }
}
