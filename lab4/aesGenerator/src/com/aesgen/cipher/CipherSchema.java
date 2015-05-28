package com.aesgen.cipher;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public interface CipherSchema{
    public byte[] encrypt(byte[] key, byte[] message, byte[] IV);
    public byte[] decrypt(byte[] key, byte[] message, byte[] IV);
}
