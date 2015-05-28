package com.company.sbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kepuss on 11.04.15.
 */
public class SboxAnalysis {
    public Sbox sbox;
    int[][] biasTab;
    int biastTabXsize;
    int biastTabYsize;

    public SboxAnalysis(Sbox sbox){
        this.sbox = sbox;
        biastTabXsize = (int)Math.pow(2,sbox.getSizeIn());
        biastTabYsize = (int)Math.pow(2,sbox.getSizeOut());
        biasTab= new int[biastTabXsize][biastTabYsize];
    }

    public void generateBiasTable(){
        for(int i=0;i<biastTabXsize;i++){
            for(int j=0;j<biastTabYsize;j++){
                biasTab[i][j]=singleBias(i,j);
            }
        }
    }

    public int singleBias(int x, int y){
        int counter=0;
        for(int i=0;i<biastTabXsize;i++){
            int output = sbox.getOut(i);
            if(checkPositions(x,i) == checkPositions(output,y)){
                counter++;
            }
        }
        return counter-biastTabXsize/2;
    }

    public int checkPositions(int a, int b){
        int c = a & b;
        if(Integer.bitCount(c)%2 ==1){
            return 1;
        }else{
            return 0;
        }
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<biastTabXsize;i++){
            for(int j=0;j<biastTabYsize;j++){
                stringBuilder.append(biasTab[i][j]+" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public List<int[]> getBiastRanking(){
        List<int[]> ranking = new ArrayList<>();
        int max=0;

        for(int i=1;i<biastTabXsize;i++){
            for(int j=1;j<biastTabYsize;j++){
                if(Math.abs(biasTab[i][j]) >=max){
                    int[] arr = new int[3];
                    arr[0] = biasTab[i][j];
                    arr[1] = i;
                    arr[2]=j;
                    max = Math.abs(biasTab[i][j]);
                    ranking.add(arr);
                }
            }
        }
        return ranking;
    }


    public Sbox getSbox() {
        return sbox;
    }

    public int[][] getBiasTab() {
        return biasTab;
    }

    public int getBiastTabXsize() {
        return biastTabXsize;
    }

    public int getBiastTabYsize() {
        return biastTabYsize;
    }
}
