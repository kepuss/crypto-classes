package com.company.equations;

import java.util.Comparator;

/**
 * Created by kepuss on 12.04.15.
 */
public class Equation{
    private int inputs;
    private int outputs;
    private float pr;

    public Equation(int uin, int out){
        inputs = uin;
        outputs = out;
    }

    public Equation(int in, int out, float pr){
        inputs = in;
        outputs = out;
        this.pr = pr;
    }

    public int getInputs() {
        return inputs;
    }

    public void setInputs(int inputs) {
        this.inputs = inputs;
    }

    public int getOutputs() {
        return outputs;
    }

    public void setOutputs(int outputs) {
        this.outputs = outputs;
    }

    public boolean ifHolds(int p, int u){

        int andP = p & inputs;
//        int andP = p & outputs;
//        int andU = u & inputs;
        int andU = u & outputs;

        if((Integer.bitCount(andP) + Integer.bitCount(andU))%2 == 1){
            return false;
        }else{
            return true;
        }


    }

    public String toString(){

        StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(String.format("%16s", Integer.toBinaryString(outputs)).replace(' ', '0') + "  " );
            stringBuilder.append(String.format("%16s", Integer.toBinaryString(inputs)).replace(' ', '0') +"  ");
            stringBuilder.append(pr + "\n");

        return stringBuilder.toString();
    }

    public float getPr() {
        return pr;
    }

    public void setPr(float pr) {
        this.pr = pr;
    }

    public Equation rotate(){
        return new Equation(inputs,outputs,pr);
    }

}
