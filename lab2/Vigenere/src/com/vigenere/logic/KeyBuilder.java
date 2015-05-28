package com.vigenere.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/15/15.
 */
public class KeyBuilder{

    public String buildFromFirst(List<List<String>> letters){
        StringBuilder stringBuilder = new StringBuilder();
        for(List<String > letter : letters){
            if(letter.get(0).equals("")){
                stringBuilder.append("-");
            }else{
                stringBuilder.append(letter.get(0));
            }
        }
        return stringBuilder.toString();
    }

    public List<String> build(List<List<String>> letters){
        List<String> results = new ArrayList< String >();
        StringBuilder current = new StringBuilder();
        permute(results,current,letters);
        return results;
    }

    private void permute(List<String> result, StringBuilder current,List<List<String>> sublist){
        StringBuilder string1 = new StringBuilder(current);
        string1.append(sublist.get(0).get(0));
        if(sublist.size()==1){
            result.add(string1.toString());
        }else{
            permute(result, string1, sublist.subList(1, sublist.size()));
        }

        StringBuilder string2 = new StringBuilder(current);
        string2.append(sublist.get(0).get(1));
        if(sublist.size()==1){
            result.add(string2.toString());
        }else{
            permute(result, string2, sublist.subList(1, sublist.size()));
        }


        StringBuilder string3 = new StringBuilder(current);
        string3.append(sublist.get(0).get(2));
        if(sublist.size()==1){
            result.add(string3.toString());
        }else{
            permute(result, string3, sublist.subList(1, sublist.size()));
        }

    }
}
