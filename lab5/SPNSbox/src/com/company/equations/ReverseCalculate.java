package com.company.equations;

import com.company.sbox.Sbox;

import java.util.List;

/**
 * Created by kepuss on 12.04.15.
 */
public class ReverseCalculate {

    private Sbox sbox;
    private Keys keys;
    private Equation equation;
    private List<int[]> PC;

    public ReverseCalculate(Sbox sbox, Keys keys, Equation equation, List<int[]> PC){
        this.sbox = sbox;
        this.keys = keys;
        this.equation = equation;
        this.PC=PC;
    }

    public void calculate(){
        int[] keyTab =keys.getPermutations(equation.getOutputs());
       // keys.getPermutationsK2K4();
        //int[] keyTab = keys.getKeys();
        float[] counter = new float[keyTab.length];
        int k=0;

        for(int key : keyTab) {
            int cnt =0;

            for(int[] pc :PC) {
                int afterKey = key^pc[1];
                int afterSbox = throughSboxs(afterKey);
                if(equation.ifHolds(pc[0],afterSbox)){
                    cnt++;
                }
            }
            counter[k]=((float)cnt-(PC.size()/2))/PC.size();
            k++;
        }
        keys.setStats(counter);
    }

        public int throughSboxs(int message){
            int[] parts = sbox.breakInt(message);
            int[] sboxed = new int[4];
            sboxed[0] = sbox.getIn(parts[0]);
            sboxed[1] = sbox.getIn(parts[1]);
            sboxed[2] = sbox.getIn(parts[2]);
            sboxed[3] = sbox.getIn(parts[3]);
            return sbox.mergeInt(sboxed);

    }

    public Keys getKeys() {
        return keys;
    }

    public int getKeyNearest(){
        float min = 100000;
        int pointer=0;
        for(int i =0 ;i <keys.getKeys().length;i++){
            if(Math.abs(Math.abs(equation.getPr()) - Math.abs(keys.getStats()[i])) < min){
                min = Math.abs(Math.abs(equation.getPr()) - Math.abs(keys.getStats()[i]));
                pointer = i;
            }
        }
        if(Math.abs(Math.abs(equation.getPr()) - Math.abs(keys.getStats()[pointer]))/Math.abs(keys.getStats()[pointer]) > 0.10){
            return -1;
        }else {
            return keys.getKeys()[pointer];
        }
    }
}
