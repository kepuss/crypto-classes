package com.company;

import com.company.equations.Equation;
import com.company.equations.Keys;
import com.company.equations.PillingUpLema;
import com.company.equations.ReverseCalculate;
import com.company.sbox.Sbox;
import com.company.sbox.SboxAnalysis;
import com.company.spnetwork.SPnetwork;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Sbox sbox = new Sbox(4,4);
        System.out.println(sbox.toString());

        SPnetwork sPnetwork = new SPnetwork(sbox);
  //      System.out.println(sPnetwork.doSPnetwork((short)21415));

        Keys keys = new Keys();
        Equation equation = new Equation(2816,1285,(float)0.336);






        SboxAnalysis sboxAnalysis = new SboxAnalysis(sbox);
        sboxAnalysis.generateBiasTable();
        System.out.println(sboxAnalysis.toString());
        for(int[] tab : sboxAnalysis.getBiastRanking()) {
            System.out.println(Arrays.toString(tab));
        }

        PillingUpLema pillingUpLema = new PillingUpLema(sboxAnalysis,sPnetwork,2);
        pillingUpLema.pill();
      //  System.out.println(pillingUpLema.sortAndreturnTwo().toString());
   //     List<Equation> eqList = pillingUpLema.sortAndreturnTwo();
       // Equation test = new Equation(eqList.get(0).getInputs(),eqList.get(0).getOutputs(), eqList.get(0).getPr());

        for(Equation equat : pillingUpLema.sortAndCut()) {
            ReverseCalculate reverseCalculate = new ReverseCalculate(sbox, keys, equat.rotate(), sPnetwork.generatePairsPC(10000));
            //  ReverseCalculate reverseCalculate = new ReverseCalculate(sbox,keys,equation,sPnetwork.generatePairsPC(10000));
            reverseCalculate.calculate();
            System.out.println(reverseCalculate.getKeys());
            int key1 = reverseCalculate.getKeyNearest();
            System.out.println(key1);
        }

//        ReverseCalculate reverseCalculate2 = new ReverseCalculate(sbox,keys,eqList.get(1).rotate(),sPnetwork.generatePairsPC(10000));
//        reverseCalculate2.calculate();
////        System.out.println(reverseCalculate.getKeys());
//        int key2=reverseCalculate2.getKeyNearest();
//
//        System.out.println(key1 + " " + key2);
//        int result = key1^key2;
//        System.out.println(result);
    }
}
