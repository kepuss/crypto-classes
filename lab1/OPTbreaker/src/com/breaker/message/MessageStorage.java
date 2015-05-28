package com.breaker.message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/7/15.
 */
public class MessageStorage{
    private List< List< Integer > > ciphers;

    public MessageStorage (){
        ciphers = new ArrayList< List< Integer > >();
    }

    public void setCiphers ( List< String > biteStrings ){
        for ( String str : biteStrings ){
            str = str.replaceAll("\\s", "");
            List< Integer > cipher = new ArrayList< Integer >();
            for ( int i = 0; i < str.length(); i += 8 ){
                String h;
//                if(i+8 >str.length() ){
//                    h= str.substring(i);
//                    cipher.add(Integer.parseInt(str.substring(i), 2));
//                }else{
                h = str.substring(i, i + 8);
                cipher.add(Integer.parseInt(str.substring(i, i + 8), 2));
//                }

            }
            this.ciphers.add(cipher);
        }
    }

    public int getMaxLength (){
        int max = 0;
        for ( List< Integer > list : ciphers ){
            if ( max < list.size() ){
                max = list.size();
            }
        }
        return max;
    }

    public String toString (){
        return ciphers.toString();
    }

    public List< List< Integer > > getCiphers (){
        return ciphers;
    }

    public List< List< Integer > > getCiphers ( int length ){
        List< List< Integer > > ciphersShort = new ArrayList<>();
        for ( List< Integer > cipher : ciphers ){
            ciphersShort.add(cipher.subList(0, length));
        }
        return ciphersShort;

    }


}
