package com.communication.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kepuss on 29.11.15.
 */
@JsonRootName(value = "SIG")
@Getter
@Setter
public class Signature {
    private String e;
    private String s;
}
