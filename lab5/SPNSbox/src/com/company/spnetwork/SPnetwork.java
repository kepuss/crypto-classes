package com.company.spnetwork;

import com.company.sbox.Sbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kepuss on 11.04.15.
 */
public class SPnetwork {
    private Sbox sbox;
    private int[] permutation;
    private int[] keys;
    private int INPUT_SIZE=16;

    public SPnetwork(Sbox sbox){
        this.sbox = sbox;
        permutation = new int[]{0,4,8,12,1,5,9,13,2,6,10,14,3,7,11,15};
        keys= new int[]{(int)22436,(int)11324,(int)22142,(int)25342,(int)41668};
    }

    public List<int[]> generatePairsPC(int n){
        List<int[]> pairs = new ArrayList<>();
        for(int i =0 ; i<n;i++){
            pairs.add(new int[]{i,doSPnetwork(i)});
        }
        return pairs;
    }

    public int doSPnetwork(int input){
        int output= (input^keys[0]);
        output = sboxing(output);
        output = permute(output);

        output= (int)(output^keys[1]);
        output = sboxing(output);
        output = permute(output);

        output= (int)(output^keys[2]);
        output = sboxing(output);
        output = permute(output);

        output= (int)(output^keys[3]);
        output = sboxing(output);

        return (int)(output^keys[4]);

    }

    public int sboxing(int kXorM){

        int[] brokenInt =  sbox.breakInt(kXorM);

//        String binaryInt = String.format("%16s", Integer.toBinaryString(kXorM)).replace(' ', '0');
//
//        int first = Integer.parseInt(binaryInt.substring(0, 4), 2);
//        int second = Integer.parseInt(binaryInt.substring(4, 8), 2);
//        int third = Integer.parseInt(binaryInt.substring(8, 12), 2);
//        int fourth = Integer.parseInt(binaryInt.substring(12, 16), 2);



        int[] results = new int[4];
        results[0] = sbox.getOut(brokenInt[0]);
        results[1] = sbox.getOut(brokenInt[1]);
        results[2] = sbox.getOut(brokenInt[2]);
        results[3] = sbox.getOut(brokenInt[3]);

       return  sbox.mergeInt(results);
    }

    public int permute(int message){
        char[] str = String.format("%16s", Integer.toBinaryString(message)).replace(' ', '0').toCharArray();
        char[] perm = "0000000000000000".toCharArray();
        for(int i=0;i<16;i++){
            perm[i]=str[permutation[i]];
        }

        return (int)Integer.parseInt(new String(perm), 2);
    }

    public int getINPUT_SIZE() {
        return INPUT_SIZE;
    }
}
