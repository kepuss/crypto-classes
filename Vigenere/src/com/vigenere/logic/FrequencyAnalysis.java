package com.vigenere.logic;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/15/15.
 */
public class FrequencyAnalysis{
    private String[] keys = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private char[] letters=  {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private double[] frequency = {8.2,1.5,2.8,4.3,12.7,2.2,2.0,6.1,7.0,0.2,0.8,4.0,2.4, 6.7,7.5,1.9,0.1,6.0,6.3,9.1,2.8,1.0,2.4,0.2,2.0,0.1};
    private double OFFSET = 7;

    public List<List<List<String>>> analyseAll( List<List<String>> prepared){
        List<List<List<String>>> possibleWords = new ArrayList< List< List< String > > >();
        for(List<String> wordList : prepared){
            List<List<String>> possibleLetters = new ArrayList< List< String > >();
            for(String word : wordList){
                possibleLetters.add(analyse(word));
            }
            possibleWords.add(possibleLetters);
        }
        return possibleWords;
    }


    public List<String> analyse(String ciphertext){
        String max1="";
        int max1V=0;
        String max2="";
        int max2V=0;
        String max3="";
        int max3V=0;

        Encryptor encryptor = new Encryptor();
        for(String key : keys){
            String encrypted = encryptor.encrypt(key,ciphertext);
            if(allowedSymbols(encrypted)){
                int count = countStatistics(encrypted);

                if ( count > max1V ){
                    max3 = max2;
                    max3V = max2V;
                    max2 = max1;
                    max2V = max1V;
                    max1 = key;
                    max1V = count;
                } else{
                    if ( count > max2V ){
                        max3 = max2;
                        max3V = max2V;
                        max2 = key;
                        max2V = count;
                    } else{
                        if ( count > max3V ){
                            max3 = key;
                            ;
                            max3V = count;
                        }
                    }
                }
            }
        }
        return new ArrayList<String>(Arrays.asList(max1,max2,max3));

    }

    public int countStatistics(String plaintext){
        int result=0;
        for(int i =0; i<letters.length;i++){
            int count = StringUtils.countMatches(plaintext,String.valueOf(letters[i]));
            count += StringUtils.countMatches(plaintext,String.valueOf(letters[i]-32));
            double resultFreq = ((double)count / plaintext.length())*100;
            if(Math.abs(resultFreq - frequency[i]) < OFFSET){
                result++;
            }
        }
        return result;
    }

    public boolean allowedSymbols (String plainText){
        char charArray[] = plainText.toCharArray();
        for(char symbol : charArray){
            if((symbol<32  || symbol> 122) && symbol != 10){
                return false;
            }
        }
        return true;
    }
}
