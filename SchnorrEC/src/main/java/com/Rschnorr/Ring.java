package com.Rschnorr;

import com.schnorr.Generator;
import com.schnorr.PublicKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kepuss on 20.12.15.
 *
 * Ring definition without current node
 */
public class Ring {
    private List<PublicKey> ringPKs;
    private int ringSize;
    private Generator gen;
    private List<PublicKey> withGenPk;


    public Ring(int ringSize, Generator gen, List<PublicKey> ringPKs) {
        this.ringSize = ringSize;
        this.gen = gen;
        this.ringPKs = ringPKs;
    }

    public Ring(Generator gen, int size) {
        this.ringSize = size;
        ringPKs = new ArrayList<PublicKey>();
        for(int i =0; i<size;i++){
            ringPKs.add(new PublicKey(gen.getRandomECPoint()));
        }
        this.gen = gen;

    }

    public List<PublicKey> getRingPKs() {
        return ringPKs;
    }
    public List<PublicKey> getAllRingPKs() {
        return  withGenPk;
    }

    public void setAllRingPKs(List<PublicKey> withGenPk) {
        this.withGenPk = new ArrayList<PublicKey>(withGenPk);
    }

    public void setRingPKs(List<PublicKey> ringPKs) {
        this.ringPKs = ringPKs;
    }

    public int getRingSize() {
        return ringSize;
    }

    public int getAllRingSize() {
        return ringSize+1;
    }

    public void setRingSize(int ringSize) {
        this.ringSize = ringSize;
    }
}
