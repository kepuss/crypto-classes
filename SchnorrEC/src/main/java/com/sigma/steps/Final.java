package com.sigma.steps;

import com.Verifiable;
import com.schnorr.Generator;
import javafx.scene.layout.BackgroundImage;
import lombok.Getter;

import java.math.BigInteger;

/**
 * Created by kepuss on 05.01.16.
 */
@Getter
public class Final {
    private byte[] K0;

    public Final(Verifiable verifier, Response response, Finish finish, Generator gen,Initialization init) {
        SigmaVerificator.sessionVerify(response.getSession(),finish.getSession());
        SigmaVerificator.signVerify00(verifier, gen, finish.getCerta(), finish.getSigna(), init, response);
        SigmaVerificator.macVerify(init.getSession(), gen, "00", finish.getCerta(), response.getK1(), finish.getMaca());

        K0 = response.getK0();
    }
}
