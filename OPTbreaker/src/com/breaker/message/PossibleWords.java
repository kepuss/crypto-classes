package com.breaker.message;

import java.util.*;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/8/15.
 */
public class PossibleWords{
    Map< Integer, List< List< Integer > > > words;

    public PossibleWords (){
        words = new HashMap<>();
    }

    public void addWord ( int index, List< Integer > word ){
        List< List< Integer > > tempList = words.get(index);
        if ( tempList == null ){
            tempList = new ArrayList<>();
        }
        tempList.add(word);
        words.put(index, tempList);
    }

    public String toString (){
        StringBuilder stringBuilder = new StringBuilder();
        Set< Integer > keySet = words.keySet();
        for ( Integer i : keySet ){
            //if(i.intValue() == 0){
            List< List< Integer > > entry = words.get(i);
            stringBuilder.append(i + ". ");
            for ( List< Integer > oneWord : entry ){
                for ( Integer letter : oneWord ){
                    stringBuilder.append(( char ) letter.intValue());
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
            // }
        }
        return stringBuilder.toString();
    }

    public Map< Integer, List< List< Integer > > > getWords (){
        return words;
    }
}
