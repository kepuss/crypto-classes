package com.vigenere.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/15/15.
 */
public class AnalysisPreparation{
    public static List<List<String>> prepare(String cipher, List<Integer> lengths){
        List<List<String>> preparedForAnalysis = new ArrayList< List< String > >();
        RepetitionFinder repetitionFinder = new RepetitionFinder();
        for(Integer length : lengths){
            preparedForAnalysis.add(repetitionFinder.prepareForAnalysis(cipher,length.intValue()));
        }
        return preparedForAnalysis;
    }
}
