package com.company.equations;

import com.company.sbox.Sbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kepuss on 12.04.15.
 */
public class Keys {

    private String[][] k;
    private String[] k2;
    private String[] k3;
    private String[] k4;
    int[] keys;
    float[] stats;

    public Keys(){
        keys = new int[65536];
        for(int i =0; i<65536;i++){
            keys[i]=i;
        }
        k=new String[4][16];

    }

    public int[] getPermutations(int in){
        int[] broken = Sbox.breakInt(in);
        int[]key=new int[16];
        int[]len = new int[4];
        for(int i=0;i<key.length;i++){
            key[i]=i;
        }
        for(int i =0 ;i<broken.length;i++){
            if(broken[i]==0){
                Arrays.fill(k[i],"0000");
                len[i]=1;
            }else{
                len[i]=k[i].length;
                for(int j=0;j<k[i].length;j++) {
                    k[i][j] = String.format("%4s", Integer.toBinaryString(j)).replace(' ', '0');
                }
            }
        }
        int[] result = new int[len[0]*len[1]*len[2]*len[3]];
        int counter=0;
        for(int i=0;i<len[0];i++){
            for(int j=0;j<len[1];j++){
                for(int w=0;w<len[2];w++){
                    for(int l=0;l<len[3];l++){
                        result[counter] = Integer.parseInt(k[0][i]+k[1][j]+k[2][w]+k[3][l] ,2);
                        counter++;
                    }
                }
            }
        }
        keys = result;
        return result;
    }

    public void getPermutationsK2K4(){
        keys = new int[256];
        for(int i = 0 ; i < 16;i++){

            for(int j = 0 ; j < 16;j++){
                String binaryI = String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0');
                String binaryJ = String.format("%4s", Integer.toBinaryString(j)).replace(' ', '0');
                String key = "0000"+binaryI+"0000"+binaryJ;
                keys[i*16+j] = Integer.parseInt(key ,2);
            }
        }
    }

    public int[] getKeys() {
        return keys;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    public float[] getStats() {
        return stats;
    }

    public void setStats(float[] stats) {
        this.stats = stats;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0 ; i < keys.length;i++) {
            stringBuilder.append(keys[i]+" "+stats[i]+"\n");
        }
        return  stringBuilder.toString();
    }
}
