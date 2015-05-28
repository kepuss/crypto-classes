package com.generator.schemes;

import com.generator.charsets.CustomCharset;

import java.io.UnsupportedEncodingException;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/6/15.
 */
public interface Scheme{
    public String cipher( String message ,CustomCharset customCharset) throws UnsupportedEncodingException;
}
