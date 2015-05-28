package com.aesgen.modes;

import com.aesgen.cipher.CipherSchema;
import com.aesgen.cipher.IVService;

import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public interface WorkMode{
    public List<byte[]> decrypt(List<byte[]> messages, CipherSchema cipherSchema, IVService ivService, byte[] key);
}
