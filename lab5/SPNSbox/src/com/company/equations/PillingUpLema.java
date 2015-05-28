package com.company.equations;

import com.company.sbox.SboxAnalysis;
import com.company.spnetwork.SPnetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by kepuss on 18.04.15.
 */
public class PillingUpLema {
    SboxAnalysis sboxAnalysis;
    SPnetwork sPnetwork;
    int depth;
    List<Equation> equations;

    public PillingUpLema(SboxAnalysis sboxAnalysis, SPnetwork sPnetwork, int depth){
        this.sboxAnalysis = sboxAnalysis;
        this.sPnetwork = sPnetwork;
        this.depth = depth;
        equations = new ArrayList<>();
    }

    public void pill(){
        //for(int i =0; i<65536; i++){
        int i = 2816;
           // recurent(i,depth,1,equations);
        recurent(i,i,depth,1,equations);

        //}
    }

    public void recurent(int input, int constIntput, int depth, float pr, List<Equation> result){

        if(Math.abs(pr) < 0.01){
            return;
        }
        if(depth ==0){
            for(Equation out : findOutputs(input)){
                if(! isInThreeParts(out.getOutputs()) && Integer.bitCount(out.getOutputs()) <6) {
                    result.add(new Equation(constIntput, out.getOutputs(), ((float) Math.pow(2, 3) * pr * (out.getPr()))));
                }
            }
        } else{
            depth--;
            for(Equation tab : findOutputs(input)){
                int nextIn = sPnetwork.permute(tab.getOutputs());

                recurent(nextIn,constIntput,depth,(float)(pr*(tab.getPr())),result);
            }
        }

    }

    public boolean isInThreeParts(int in){
        int[] broken = sboxAnalysis.sbox.breakInt(in);
        int counter=0;
        for(int i=0;i<broken.length;i++){
            if(broken[i]==0){
                counter++;
            }
        }
        if(counter == 2){
            return false;
        }else{
            return true;
        }
    }

    public List<Equation> findOutputs(int in){
        int[] input= sboxAnalysis.sbox.breakInt(in);
        List<List<int[]>> posibilities = new ArrayList<>();
        for(int oneIn : input){
            posibilities.add(findOutputsForSbox(oneIn));
        }
        List<Equation> eq = new ArrayList<>();
        for(int[] first : posibilities.get(0)){
            for(int[] second : posibilities.get(1)){
                for(int[] third : posibilities.get(2)){
                    for(int[] forth : posibilities.get(3)){
                        int result[] = new int[4];
                        float probability=1;
                        int multiply =-1;
                        result[0] = first[0];
                        if(first[0] !=0){
                            probability *= (float)(((float)first[1]/16)) ;
                            multiply++;
                        }
                        result[1] = second[0];
                        if(second[0] !=0){
                            probability *= (float)(((float)second[1]/16));
                            multiply++;
                        }
                        result[2] = third[0];
                        if(third[0] !=0){
                            probability *= (float)(((float)third[1]/16));
                            multiply++;
                        }
                        result[3] = forth[0];
                        if(forth[0] !=0){
                            probability *= (float)(((float)forth[1]/16));
                            multiply++;
                        }
                        int output = sboxAnalysis.sbox.mergeInt(result);
                       // eq.add(new Equation(in,output,(float)0.5+(float)(Math.pow(2,multiply)*probability)));
                        eq.add(new Equation(in,output,(float)probability));
                    }
                }
            }
        }
return eq;
    }


    public List<int[]> findOutputsForSbox(int in){
        List<int[]> outputs = new ArrayList<>();

            for (int i = 0; i < sboxAnalysis.getBiastTabYsize(); i++) {
                if (sboxAnalysis.getBiasTab()[in][i] != 0) {
                    outputs.add(new int[]{i, sboxAnalysis.getBiasTab()[in][i]});
                }
            }
        return outputs;

    }




    public int findNext(int in){
        int afterSbox = sboxAnalysis.getSbox().throughSboxes(in);
        return sPnetwork.permute(afterSbox);
    }

    public float levelProbability(int in, int out){
        int[] brokenIn = sboxAnalysis.sbox.breakInt(in);
        int[] brokenOut = sboxAnalysis.sbox.breakInt(out);

        int counter=1;
        float pr=1;
        for(int i =0;i<4;i++) {
            if(brokenIn[i] !=0){
                counter++;
            }
            pr *= (0.5 -sboxAnalysis.checkPositions(brokenIn[i], brokenOut[i]));
        }
        pr*=Math.pow(2,counter);
        return pr;
    }

    public List<Equation> getEquations() {
        return equations;
    }

    public List<Equation> sortAndreturnTwo(){
        Collections.sort(equations, (Equation s1, Equation s2) ->{
            return Float.valueOf(Math.abs(s2.getPr())).compareTo(Float.valueOf(Math.abs(s1.getPr())));
        });
        Equation first = equations.get(0);
//        for(int i=0;i<equations.size();i++){
//            if((equations.get(i).getOutputs() & 61680) == equations.get(i).getOutputs() && equations.get(i).getOutputs() > 4095){
//                first = equations.get(i);
//                break;
//            }
//        }
        Equation second = equations.get(2);

//        for(int i=0;i<equations.size();i++){
//            if((equations.get(i).getOutputs() & 3855) == equations.get(i).getOutputs() && equations.get(i).getOutputs() > 255){
//                second = equations.get(i);
//                break;
//            }
//        }
        return new ArrayList<Equation>(Arrays.asList(first,second));

    }

    public List<Equation> sortAndCut(){
        Collections.sort(equations, (Equation s1, Equation s2) ->{
            return Float.valueOf(Math.abs(s2.getPr())).compareTo(Float.valueOf(Math.abs(s1.getPr())));
        });
        return equations.subList(0,50);

    }
}
