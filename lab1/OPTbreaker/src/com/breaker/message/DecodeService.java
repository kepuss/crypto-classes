package com.breaker.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/8/15.
 */
public class DecodeService{
    public static List< List< Integer > > decode ( List< List< Integer > > ciphers, List< Integer > key ){
        List< List< Integer > > decodedCiphers = new ArrayList<>();
        for ( List< Integer > cipher : ciphers ){
            List< Integer > decoded = new ArrayList<>();
            int minSize = cipher.size() > key.size() ? key.size() : cipher.size();
            for ( int i = 0; i < minSize; i++ ){
                decoded.add(cipher.get(i) ^ key.get(i));
            }
            decodedCiphers.add(decoded);
        }
        return decodedCiphers;
    }

    public static List< List< Integer > > decodePart ( List< List< Integer > > ciphers, List< Integer > key, int start, int length ){

        List< List< Integer > > decodedCiphers = new ArrayList<>();
        for ( List< Integer > cipher : ciphers ){
            if ( cipher.size() < start + length ){
                Integer[] tempCipher = new Integer[ length ];
                Arrays.fill(tempCipher, 120);
                decodedCiphers.add(new ArrayList< Integer >(Arrays.asList(tempCipher)));

            } else{
                cipher = cipher.subList(start, start + length);
                List< Integer > decoded = new ArrayList<>();
                int minSize = cipher.size() > key.size() ? key.size() : cipher.size();
                for ( int i = 0; i < minSize; i++ ){
                    decoded.add(cipher.get(i) ^ key.get(i));
                }
                decodedCiphers.add(decoded);
            }
        }
        return decodedCiphers;
    }

    public static String toString ( List< List< Integer > > ciphers ){
        StringBuilder stringBuilder = new StringBuilder();
        for ( List< Integer > cipher : ciphers ){
            for ( Integer letter : cipher ){
                stringBuilder.append(( char ) letter.intValue());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();

    }

    public static String toString ( List< List< Integer > > ciphers, int start, int length ){
        StringBuilder stringBuilder = new StringBuilder();
        for ( List< Integer > cipher : ciphers ){
            if ( cipher.size() <= start + length + 2 ){
                stringBuilder.append("---------");

            } else{
                cipher = cipher.subList(start, start + length);
                for ( Integer letter : cipher ){
                    stringBuilder.append(( char ) letter.intValue());
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();

    }

    public static List< Integer > stringToList ( String str ){
        char[] strChar = str.toCharArray();
        List< Integer > list = new ArrayList<>();
        for ( char letter : strChar ){
            list.add(Integer.valueOf(( int ) letter));
        }
        return list;
    }

    public static String listToString ( List< Integer > list ){
        StringBuilder stringBuilder = new StringBuilder();
        for ( Integer letter : list ){
            stringBuilder.append(( char ) letter.intValue());
        }
        return stringBuilder.toString();
    }
}
