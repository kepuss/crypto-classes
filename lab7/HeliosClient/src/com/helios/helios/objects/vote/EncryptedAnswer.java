package com.helios.helios.objects.vote;

import com.helios.helios.objects.election.Election;
import com.helios.helios.objects.election.ElgamalPublicKey;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kepuss on 16.05.15.
 */

public class EncryptedAnswer {
    private List<ElgamalCiphertext> choices;
    private List<List<ZKProof>> individual_proofs;
    private List<ZKProof> overall_proof;
    private List<Integer> answer;
    private List<BigInteger> randomness;
    private final BigInteger possibleAnswers[];

    public EncryptedAnswer(Election el, int selection) throws IOException, NoSuchAlgorithmException {
        ElgamalPublicKey pub = el.getPublic_key();

        possibleAnswers = new BigInteger[] {el.getPublic_key().getGBigInt().pow(0),el.getPublic_key().getGBigInt().pow(1)};

        randomness = new ArrayList<>();
        randomness.add(generateRandomness(pub.getQBigInt().bitLength()));

        choices = new ArrayList<>();


        choices.add(new ElgamalCiphertext(pub, possibleAnswers[selection],randomness.get(0)));
        individual_proofs = new ArrayList<>();
        individual_proofs.add(generateListOfProofs(el,selection));

        overall_proof = generateListOfOveralProofs(el,selection);
    }

    private BigInteger generateRandomness(int length){
        Random rand = new Random();
        BigInteger random = new BigInteger(length,rand);
        return random;
    }

    private List<ZKProof> generateListOfOveralProofs(Election el,int selected) throws IOException, NoSuchAlgorithmException {
        List<ZKProof> overall = new ArrayList<>();


            BigInteger allChallenge = ZKProof.generateChallenge(individual_proofs.get(0).get(0).getCommitment(), individual_proofs.get(0).get(1).getCommitment());
            System.out.println("all "+allChallenge);
            BigInteger realchall2 = allChallenge.subtract(new BigInteger(individual_proofs.get(0).get((selected+1)%2).getChallenge())).mod(el.getPublic_key().getQBigInt());
        if(selected == 0) {
            overall.add(new ZKProof(el, randomness.get(0), individual_proofs.get(0).get(0).getCommitment(), realchall2));
            overall.add(individual_proofs.get(0).get(1));
        }else{
            overall.add(individual_proofs.get(0).get(0));
            overall.add(new ZKProof(el, randomness.get(0), individual_proofs.get(0).get(1).getCommitment(), realchall2));

        }
        System.out.println(individual_proofs.get(0).get(0).getChallengeBigInt());
        System.out.println(individual_proofs.get(0).get(1).getChallengeBigInt());
        System.out.println("sum "+individual_proofs.get(0).get(0).getChallengeBigInt().add(individual_proofs.get(0).get(1).getChallengeBigInt()).mod(el.getPublic_key().getQBigInt()));

        return overall;
    }

    private List<ZKProof> generateListOfProofs(Election el,int selected) throws IOException, NoSuchAlgorithmException {
        List<ZKProof> listOfProofs = new ArrayList<>();

        answer = new ArrayList<>();
        if(selected ==1) {
             answer.add(Integer.valueOf(0));
        }


        ZKProof simulatedProof = new ZKProof(el,randomness.get(0),possibleAnswers[(selected+1)%2],choices.get(0),true);
        BigInteger disjunctiveChall = new BigInteger(simulatedProof.getChallenge());
        ZKProof realProof = new ZKProof(el, randomness.get(0), disjunctiveChall,simulatedProof.getCommitment(),selected);

if(selected==0) {
    listOfProofs.add(realProof);
    listOfProofs.add(simulatedProof);
}else{
    listOfProofs.add(simulatedProof);
    listOfProofs.add(realProof);
}
        return listOfProofs;
    }

    public List<ElgamalCiphertext> getChoices() {
        return choices;
    }

    public void setChoices(List<ElgamalCiphertext> choices) {
        this.choices = choices;
    }

    public List<List<ZKProof>> getIndividual_proofs() {
        return individual_proofs;
    }

    public void setIndividual_proofs(List<List<ZKProof>> individual_proofs) {
        this.individual_proofs = individual_proofs;
    }

    public List<ZKProof> getOverall_proof() {
        return overall_proof;
    }

    public void setOverall_proof(List<ZKProof> overall_proof) {
        this.overall_proof = overall_proof;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public List<String> getRandomness() {
        List<String> list = new ArrayList<>();
        list.add(randomness.get(0).toString());
        return list;
    }

    public void setRandomness(List<BigInteger> randomness) {
        this.randomness = randomness;
    }
}
