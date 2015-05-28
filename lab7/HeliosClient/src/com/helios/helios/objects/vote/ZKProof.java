package com.helios.helios.objects.vote;

import com.helios.helios.objects.election.Election;
import com.helios.service.Crypto;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by kepuss on 16.05.15.
 */

public class ZKProof {
    private String challenge;
    private Commitment commitment;
    private String response;

    public ZKProof(Election el, BigInteger randomness, BigInteger challenge, Commitment comm, int selected) throws IOException, NoSuchAlgorithmException {
      // Challenge chal = new Challenge(el,m);
        //HttpJsonReader<Challenge> readerAn = new HttpJsonReader<>();


        commitment = new Commitment(el.getPublic_key());
        //response = new BigInteger(challenge).multiply(ciphertext.getR()).add(chal.getW()).toString();
        System.out.println("sukane " + generateChallenge(commitment, comm).toString());
        System.out.println("odejmowane " + challenge.toString());
        if(selected == 0) {
            this.challenge = generateChallenge(commitment, comm).subtract(challenge).mod(el.getPublic_key().getQBigInt()).toString();
        }else{
            this.challenge = generateChallenge( comm,commitment).subtract(challenge).mod(el.getPublic_key().getQBigInt()).toString();

        }

     //   this.challenge = challenge.toString();
        System.out.println("wynik "+this.challenge);
        response = randomness.multiply(new BigInteger(this.challenge)).add(commitment.getW()).mod(el.getPublic_key().getQBigInt()).toString();
    }

    public ZKProof(Election el, BigInteger randomness) throws IOException, NoSuchAlgorithmException {
        // Challenge chal = new Challenge(el,m);
        //HttpJsonReader<Challenge> readerAn = new HttpJsonReader<>();


        commitment = new Commitment(el.getPublic_key());
        //response = new BigInteger(challenge).multiply(ciphertext.getR()).add(chal.getW()).toString();
        //  challenge = generateChallenge(commitment).toString();
        this.challenge = generateChallenge(commitment).toString();
        response = randomness.multiply(new BigInteger(this.challenge)).add(commitment.getW()).mod(el.getPublic_key().getQBigInt()).toString();
    }

    public ZKProof(Election el, BigInteger randomness, Commitment comm, BigInteger chall) throws IOException, NoSuchAlgorithmException {
        commitment = comm;
        this.challenge = chall.toString();
        response = randomness.multiply(new BigInteger(this.challenge)).add(commitment.getW()).mod(el.getPublic_key().getQBigInt()).toString();
    }

    public ZKProof(Election el, BigInteger randomness,Commitment otherCommitment) throws IOException, NoSuchAlgorithmException {
        // Challenge chal = new Challenge(el,m);
        //HttpJsonReader<Challenge> readerAn = new HttpJsonReader<>();


        commitment = new Commitment(el.getPublic_key());
        //response = new BigInteger(challenge).multiply(ciphertext.getR()).add(chal.getW()).toString();
        //  challenge = generateChallenge(commitment).toString();
        this.challenge = generateChallenge(commitment,otherCommitment).toString();
        response = randomness.multiply(new BigInteger(this.challenge)).add(commitment.getW()).mod(el.getPublic_key().getQBigInt()).toString();
    }

    public ZKProof(Election el, BigInteger randomness, BigInteger m, ElgamalCiphertext cipher, boolean simulate){
        byte[] b = new byte[el.getPublic_key().getQBigInt().toByteArray().length];
        int length = el.getPublic_key().getQBigInt().bitLength();
        //new Random().nextBytes(b);
        Random rand = new Random();
       //challenge = new BigInteger(1,b).toString();
        challenge = new BigInteger(length,rand).toString();
        new Random().nextBytes(b);
 //       response = new BigInteger(1,b).toString();
        response = new BigInteger(length,rand).toString();
        BigInteger betaOverPlain = m.modInverse(el.getPublic_key().getPBigInt()).multiply(cipher.getBetaBigInt()).mod(el.getPublic_key().getPBigInt());
        BigInteger Ainverse = cipher.getAlphaBigInt().modPow(new BigInteger(challenge), el.getPublic_key().getPBigInt()).modInverse(el.getPublic_key().getPBigInt());
        BigInteger A =el.getPublic_key().getGBigInt().modPow(new BigInteger(response), el.getPublic_key().getPBigInt()).multiply(Ainverse).mod(el.getPublic_key().getPBigInt());
        BigInteger Binverse = betaOverPlain.modPow(new BigInteger(challenge), el.getPublic_key().getPBigInt()).modInverse(el.getPublic_key().getPBigInt());
        BigInteger B = el.getPublic_key().getYBigInt().modPow(new BigInteger(response), el.getPublic_key().getPBigInt()).multiply(Binverse).mod(el.getPublic_key().getPBigInt());
        commitment = new Commitment(A,B);

    }

    public BigInteger generateChallenge(Commitment comm){
        String chall = comm.getA() + ','+comm.getB();
        return new BigInteger("0"+Crypto.sha1AndHex(chall),16);

    }

    public static BigInteger generateChallenge(Commitment comm1,Commitment comm2){
        String chall = comm1.getA() + ','+comm1.getB()+','+comm2.getA() + ','+comm2.getB();
        String hex= Crypto.sha1AndHex(chall);
        System.out.println(hex);
        return new BigInteger("0"+Crypto.sha1AndHex(chall),16);

    }

    public String getResponse() {
        return response;
    }

    @JsonIgnore
    public BigInteger getResponseBigInt() {
        return new BigInteger(response);
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getChallenge() {
        return challenge;
    }

    @JsonIgnore
    public BigInteger getChallengeBigInt() {
        return new BigInteger(challenge);
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public Commitment getCommitment() {
        return commitment;
    }

    public void setCommitment(Commitment commitment) {
        this.commitment = commitment;
    }
}
