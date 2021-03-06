package com.communication.model;

import com.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sigma.Sendable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kepuss on 29.11.15.
 */
@JsonRootName(value = "CERT")

public class Certificate implements Sendable{
    @Getter
    @Setter
    private Body body;

    @JsonIgnore
    private Signature signature;

    @JsonIgnore
    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }


    @JsonProperty(value = "sign")
    public Sendable getSignSelect(){

        if(signature.getE() ==null){
            return new RSignature(signature);
        }else{
            return new SimpleSignature(signature);
        }

    }

    @JsonProperty(value = "sign")
    public void setSignSelect(SimpleSignature signature){
        this.signature = signature.getSignature();

//        if(signature.contains("R_y")){
//            this.signature = Utils.mapObject(RSignature.class, signature).getSignature();
//        }else{
//            this.signature = Utils.mapObject(SimpleSignature.class, signature).getSignature();
//        }

    }
}
