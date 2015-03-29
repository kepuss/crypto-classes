package com.breaker.message;

import java.util.*;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/8/15.
 */
public class SuitedWords{
    Map< Integer, List< List< Integer > > > words;

    public SuitedWords (){
        words = new HashMap<>();
    }

    public void addWords ( Integer position, List< Integer > word ){
        List< List< Integer > > tempList = words.get(position);
        if ( tempList == null ){
            tempList = new ArrayList<>();
        }
        tempList.add(word);
        words.put(position, tempList);
    }

    public String toString (){
        StringBuilder stringBuilder = new StringBuilder();
        Set< Integer > keys = words.keySet();
        for ( Integer key : keys ){
            stringBuilder.append(key + ". ");
            List< List< Integer > > wordList = words.get(key);
            for ( List< Integer > word : wordList ){
                for ( Integer letter : word ){
                    stringBuilder.append(( char ) letter.intValue());
                }
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
