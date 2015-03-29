package com.vigenere.logic;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/15/15.
 */
public class RepetitionFinder{
    public List<Integer>  findRepetitions(String cipher){
        Set<String> blackList = new HashSet< String >();
        List<Integer> distances = new ArrayList< Integer >();
        for(int i=0; i<cipher.length()-3;i++){
            String subStr = cipher.substring(i,i+3);
            if(!blackList.contains(subStr)){
                distances.addAll(lookForRepetitions(cipher,subStr));
                blackList.add(subStr);
            }
        }
        return distances;
    }

    private List<Integer>  lookForRepetitions(String cipher, String word){
        int tempIndex = 1;
        Set<Integer> occurences = new HashSet< Integer >();
        String temp = cipher;
        while ( temp.indexOf(word) != -1 ){
            int index = temp.indexOf(word);
            occurences.add(Integer.valueOf(index));
            tempIndex = index+1;
            temp = temp.substring(index+1);
        }

        List<Integer> distances = new ArrayList< Integer >();
        if(occurences.size()>1){
            List<Integer> occurencesList = new ArrayList< Integer >(occurences);
            for(int i =0 ; i< occurencesList.size();i++){
                for(int j=i+1; j<occurencesList.size()-1;j++){
                    distances.add(Integer.valueOf(Math.abs(occurencesList.get(i)-occurencesList.get(j))));
                }
            }
        }
        return distances;
    }


    public List<String> prepareForAnalysis(String cipher, int n){
        List<String> fragments = new ArrayList< String >();
        for(int i = 0; i<n;i++){
            fragments.add(takeEveryNLetter(cipher,n,i));
        }
        return fragments;
    }

    public String takeEveryNLetter(String cipher, int n, int order){
        char[] cipherArray = cipher.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i+order<cipher.length(); i+=n){
            stringBuilder.append(cipherArray[i+order]);
        }
        return stringBuilder.toString();
    }
}
