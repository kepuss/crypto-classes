package com.communication.model;

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
@Getter
@Setter
public class Certificate implements Sendable{
    @JsonProperty("BODY")
    private Body body;
    @JsonProperty("SIGN")
    private Signature signature;
}
