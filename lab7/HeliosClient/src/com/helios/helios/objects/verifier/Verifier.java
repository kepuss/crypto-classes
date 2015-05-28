package com.helios.helios.objects.verifier;

import com.helios.helios.objects.election.Election;
import com.helios.helios.objects.vote.Vote;
import com.helios.helios.objects.vote.ZKProof;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by kepuss on 23.05.15.
 */
public class Verifier {
    private Vote vote;
    private Election el;
    private BigInteger[] possiblePlaintexts;

    public Verifier(Vote vote, Election el){
        this.vote = vote;
        this.el = el;
        possiblePlaintexts = new BigInteger[2];
        possiblePlaintexts[0]=BigInteger.ONE;
        possiblePlaintexts[1]=el.getPublic_key().getGBigInt().pow(1);
    }

    public boolean verify(){
        List<ZKProof> listProof =vote.getAnswers().get(0).getIndividual_proofs().get(0);
        List<ZKProof> listOverallProof =vote.getAnswers().get(0).getOverall_proof();
        if(!verifyEncryptionProof(0, listProof.get(0))){ return false; }
        if(!verifyEncryptionProof(1, listProof.get(1))){ return false; }
        if(!verifyEncryptionProof(0, listOverallProof.get(0))){ return false; }
        if(!verifyEncryptionProof(1, listOverallProof.get(1))){ return false; }
        if(!checkOverall(listProof)){ return false; }
        return true;

    }
    public boolean verifyEncryptionProof(int plaintext, ZKProof proof){
        BigInteger firstCond = el.getPublic_key().getGBigInt().modPow(proof.getResponseBigInt(), el.getPublic_key().getPBigInt());
        BigInteger alpha = vote.getAnswers().get(0).getChoices().get(0).getAlphaBigInt();
        BigInteger secCond = alpha.modPow(proof.getChallengeBigInt(),el.getPublic_key().getPBigInt()).multiply(proof.getCommitment().getABigInt()).mod(el.getPublic_key().getPBigInt());
        if(!firstCond.equals(secCond)){
            return false;
        }

        BigInteger beta = vote.getAnswers().get(0).getChoices().get(0).getBetaBigInt();
        BigInteger betaOverM = possiblePlaintexts[plaintext].modInverse(el.getPublic_key().getPBigInt()).multiply(beta).mod(el.getPublic_key().getPBigInt());
        firstCond = el.getPublic_key().getYBigInt().modPow(proof.getResponseBigInt(),el.getPublic_key().getPBigInt());
        secCond = betaOverM.modPow(proof.getChallengeBigInt(),el.getPublic_key().getPBigInt()).multiply(proof.getCommitment().getBBigInt()).mod(el.getPublic_key().getPBigInt());

        if(!firstCond.equals(secCond)){
            return false;
        }

        return true;
    }

    public boolean checkOverall(List<ZKProof> individual){
        BigInteger gen = ZKProof.generateChallenge(individual.get(0).getCommitment(),individual.get(1).getCommitment());
        BigInteger sum = individual.get(0).getChallengeBigInt().add(individual.get(1).getChallengeBigInt()).mod(el.getPublic_key().getQBigInt());
        if(gen.equals(sum)){
            return true;
        }
        return false;
    }


}
