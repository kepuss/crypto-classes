package com.communication.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kepuss on 29.11.15.
 */
@JsonRootName(value = "BODY")
@Getter
@Setter
public class Body {
    private String key_holder;
    private String pub_key;
    private String par;
    private String hash;
    private String ca_name;



}
