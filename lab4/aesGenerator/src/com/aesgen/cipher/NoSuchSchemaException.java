package com.aesgen.cipher;

import java.lang.reflect.Executable;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class NoSuchSchemaException extends Exception{
    public NoSuchSchemaException(String message) {
        super(message);
    }

    public NoSuchSchemaException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
