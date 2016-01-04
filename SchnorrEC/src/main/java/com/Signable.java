package com;

import com.communication.model.Signature;

/**
 * Created by kepuss on 04.01.16.
 */
public interface Signable {
    Signature sign(String message);
}
