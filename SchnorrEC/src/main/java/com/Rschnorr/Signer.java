package com.Rschnorr;

import com.Signable;
import com.schnorr.Generator;
import com.schnorr.Hasher;
import com.schnorr.PublicKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kepuss on 20.12.15.
 */
public class Signer implements Signable{

    private Ring ring;
    private Generator gen;

    public Signer(Ring ring, Generator gen) {
        this.ring = ring;
        this.gen = gen;
    }

    private BigInteger randAlistandRlist(List<BigInteger> aList,List<ECPoint> RList){
        BigInteger aSum = BigInteger.ZERO;
        for(int i =0;i <ring.getRingSize();i++){
            BigInteger rand = gen.getRandomBigInt();
            aList.add(rand);
            //Don't know if can transform to BIgInteger
            RList.add(gen.getECPoint(rand, gen.getG()));
            aSum = aSum.add(rand);
        }
        return aSum;
    }

    private ECPoint geberateRsAndHashes(BigInteger a, String message,  List<BigInteger> hashList, List<ECPoint> RList){
        ECPoint ga = gen.getECPoint(a, gen.getG());
        ECPoint multiY= null;
        for(int i =0;i <ring.getRingSize();i++){
            BigInteger hash = new BigInteger(1,Hasher.hash(message,RList.get(i).normalize().getRawXCoord().toBigInteger(),gen.getHashName()));
            hashList.add(hash);
            if(multiY == null){
                multiY = gen.getECPoint(hash.negate(),ring.getRingPKs().get(i).getP());
            }else{
                multiY = multiY.add(gen.getECPoint(hash.negate(),ring.getRingPKs().get(i).getP()));
            }
        }
        return ga.add(multiY);
    }

    public com.communication.model.Signature sign(String message){
        //0
        Random rand = new Random();
        int position = rand.nextInt(ring.getRingSize());
        List<PublicKey> withGenPk = new ArrayList<PublicKey>(ring.getRingPKs());
        withGenPk.add(position,gen.getPk());
        ring.setAllRingPKs(withGenPk);


        // 1
        List<BigInteger> aList = new ArrayList<BigInteger>();
        List<ECPoint> RList = new ArrayList<ECPoint>();
        BigInteger aSum = randAlistandRlist(aList, RList);
        // 2
        BigInteger a = gen.getRandomBigInt();
        // 3
        List<BigInteger> hashList = new ArrayList<BigInteger>();
        ECPoint Rs = geberateRsAndHashes(a,message,hashList,RList);

        RList.add(position,Rs);

        // 4
        BigInteger sigma = generateSigma(message,Rs,hashList,position,a,aSum);

//        System.out.println("Sigma: "+sigma);
//        System.out.println("Rs: "+Rs);
//        System.out.println("g^x: "+new BigInteger(gen.getECPoint(gen.getSk().getA(), gen.getG()).getEncoded(false)));
//        System.out.println("g^sigma: "+new BigInteger(gen.getECPoint(sigma, gen.getG()).getEncoded(false)).mod(gen.getN()));
//        System.out.println("y: "+new BigInteger(gen.getPk().getP().getEncoded(false)));
//        System.out.println("g^a: " + new BigInteger(gen.getECPoint(a.add(aSum),gen.getG()).getEncoded(false)).mod(gen.getN()));

        return new com.communication.model.Signature(message,RList,hashList,sigma);

    }

    private BigInteger generateSigma(String message, ECPoint Rs,List<BigInteger> hashList, int position, BigInteger a, BigInteger aSum ){
        BigInteger orgHash = new BigInteger(1,Hasher.hash(message, Rs.normalize().getRawXCoord().toBigInteger(), gen.getHashName()));
        hashList.add(position,orgHash);
        BigInteger sigEnd = gen.getSk().getA().multiply(orgHash);//.mod(gen.getN());
        return a.add(aSum).add(sigEnd).mod(gen.getN());
    }

    private ECPoint decodePoint(BigInteger point){
        return gen.getG().getCurve().decodePoint(point.toByteArray());
    }
}
