package com.breaker.charsets;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/6/15.
 */
public enum CustomCharset{

    UTF8("UTF-8");

    private final String name;

    private CustomCharset ( String s ){
        name = s;
    }

    public String toString (){
        return name;
    }
}
