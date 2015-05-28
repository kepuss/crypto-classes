package com.aesgen.modes;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class NoSuchModeException extends  Exception{
    public NoSuchModeException(String message) {
        super(message);
    }

    public NoSuchModeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
