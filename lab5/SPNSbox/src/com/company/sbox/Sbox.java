package com.company.sbox;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by kepuss on 11.04.15.
 */
public class Sbox {
    private int[][] lookUp= {{14, 4, 13, 1}, {2 ,15 ,11, 8}, {3 ,10 ,6 ,12}, {5, 9, 0, 7}};

    private int halfSize;
    private int sizeIn;
    private int sizeOut;

    public Sbox(int sizeIn, int sizeOut){
        this.sizeIn = sizeIn;
        this.sizeOut = sizeOut;
        int limit = (int)Math.pow(2,sizeIn);
        halfSize=(int)Math.pow((double)(sizeIn/2),(double)2);
 //       lookUp = new int[halfSize][halfSize];

 //       lookUp = {{13, 14, 1, 7}, {5 ,8 ,10, 11}, {12 ,3 ,0 ,2}, {4, 15, 6, 9}};

//        Random r = new Random();
//        Set<Integer> unique = new HashSet<>();
//        for(int i=0;i<halfSize;i++){
//            for(int j=0;j<halfSize;j++){
//                int rand = r.nextInt(limit);
//                int size = unique.size();
//                unique.add(Integer.valueOf(rand));
//                if(size<unique.size()) {
//                    lookUp[i][j] = rand;
//                }else{
//                    j--;
//                }
//            }
//        }
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<halfSize;i++){
            for(int j=0;j<halfSize;j++){
                stringBuilder.append(lookUp[i][j]+" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public int[][] getLookUp() {
        return lookUp;
    }

    public int getOut(int in){

        String binaryInt = String.format("%"+sizeIn+"s", Integer.toBinaryString(in)).replace(' ', '0');

        int first = Integer.parseInt(binaryInt.substring(0, sizeIn/2), 2);
        int second = Integer.parseInt(binaryInt.substring(sizeIn/2,sizeIn), 2);


        return lookUp[first][second];
    }

    public int getIn(int out){

        int inSel=0;
        int outSel =0;
        for(int i=0;i<halfSize;i++){
            for(int j=0;j<halfSize;j++){
                if(lookUp[i][j] == out){
                    inSel=i;
                    outSel=j;
                    break;
                }
            }
        }

        String binaryIntIn = String.format("%"+sizeIn/2+"s", Integer.toBinaryString(inSel)).replace(' ', '0');
        String binaryIntOut = String.format("%"+sizeIn/2+"s", Integer.toBinaryString(outSel)).replace(' ', '0');

        return Integer.parseInt((binaryIntIn+binaryIntOut), 2);
    }

    public int getHalfSize() {
        return halfSize;
    }

    public int getSizeIn() {
        return sizeIn;
    }

    public int getSizeOut() {
        return sizeOut;
    }

    public static int[] breakInt(int in){
        String binaryInt = String.format("%16s", Integer.toBinaryString(in)).replace(' ', '0');

        int[] tab = new int[4];

        tab[0] = Integer.parseInt(binaryInt.substring(0, 4), 2);
        tab[1] = Integer.parseInt(binaryInt.substring(4, 8), 2);
        tab[2] = Integer.parseInt(binaryInt.substring(8,12), 2);
        tab[3] = Integer.parseInt(binaryInt.substring(12,16), 2);

        return tab;
    }

    public int mergeInt(int[] in){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%4s", Integer.toBinaryString(in[0])).replace(' ', '0'));
        stringBuilder.append(String.format("%4s", Integer.toBinaryString(in[1])).replace(' ', '0'));
        stringBuilder.append(String.format("%4s", Integer.toBinaryString(in[2])).replace(' ', '0'));
        stringBuilder.append(String.format("%4s", Integer.toBinaryString(in[3])).replace(' ', '0'));

        return Integer.parseInt(stringBuilder.toString(), 2);
    }

    public int throughSboxes(int in){
        int[] broken=breakInt(in);
        int[] result = new int[4];
        result[0] = getOut(broken[0]);
        result[1] = getOut(broken[1]);
        result[2] = getOut(broken[2]);
        result[3] = getOut(broken[3]);
        return mergeInt(result);
    }
}
