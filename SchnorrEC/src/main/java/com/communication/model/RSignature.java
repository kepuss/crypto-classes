package com.communication.model;

import com.Utils;
import com.sigma.Sendable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kepuss on 06.01.16.
 */
@Getter
@Setter
public class RSignature implements Sendable {
    private String R_A;
    private String R_B;
    private String h_A;
    private String h_B;
    private String sigma;

    public RSignature(Signature signature) {
        R_A = Utils.ecToString(signature.getR().get(0));
        R_B = Utils.ecToString(signature.getR().get(1));

        h_A = signature.getH().get(0).toString(16);
        h_B = signature.getH().get(1).toString(16);

        sigma = signature.getSigma().toString(16);
    }
}
