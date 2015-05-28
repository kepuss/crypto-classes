package com.vigenere.logic;

import java.util.*;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/15/15.
 */
public class FactorStatistic{
    public Map<Integer,Integer> calculateFactors(List<Integer> numbers){
        List<Integer> factors = new ArrayList< Integer >();
        for(Integer number : numbers){
            factors.addAll(getFactors(number));
        }
        return  getOccurences(factors);
    }

    private List<Integer> getFactors(Integer number){
        List<Integer> factors = new ArrayList<Integer>();
        factors.add(number);
        int n = number.intValue();
        for(int test = n - 1; test >= Math.sqrt(n); test--){
            if ( n % test == 0 ){
                factors.add(Integer.valueOf(test));
                factors.add(Integer.valueOf(n/test));
            }
        }
        
        return factors;
    }

    private Map<Integer,Integer> getOccurences(List<Integer> factors){
        Set<Integer> elemets = new HashSet< Integer >(factors);
        Map<Integer,Integer> occurences = new HashMap< Integer, Integer >();

        for(Integer elem : elemets){
            int occurence =0;
            for(Integer i : factors){
                if(elem.equals(i)){
                    occurence++;
                }
            }
            occurences.put(elem,occurence);
        }
        return  occurences;
    }

    public List<Integer> takeThreePossibleKeyLength(Map<Integer,Integer> possibleKeys){

        List<Integer> sortedList = new ArrayList< Integer >();

        while(possibleKeys.size() >0){
            Integer max = 0;
            Integer  maxV =0;
            for(Map.Entry<Integer,Integer> entry : possibleKeys.entrySet()){
                if(entry.getValue() > max){
                    max = entry.getValue();
                    maxV = entry.getKey();
                }
            }
            sortedList.add(maxV);
            possibleKeys.remove(maxV);
        }
        return sortedList;
    }
}
