package com.breaker.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/7/15.
 */
public class XorService{
    private List< List< Integer > > xors;
    private List< List< Integer > > order;

    public XorService ( MessageStorage messageStorage ){
        xors = new ArrayList< List< Integer > >();
        order = new ArrayList< List< Integer > >();
        for ( int i = 0; i < messageStorage.getCiphers().size(); i++ ){
            for ( int j = i + 1; j < messageStorage.getCiphers().size(); j++ ){
                order.add(new ArrayList<>(Arrays.asList(i, j)));
                xors.add(xorLists(i, j, messageStorage.getCiphers()));
            }
        }

    }

    private List< Integer > xorLists ( int indexX, int indexY, List< List< Integer > > messages ){
        List< Integer > listX = messages.get(indexX);
        List< Integer > listY = messages.get(indexY);

        List< Integer > listResult = new ArrayList< Integer >();

        int minSize = listX.size() > listY.size() ? listY.size() : listX.size();
        for ( int i = 0; i < minSize; i++ ){
            listResult.add(listX.get(i) ^ listY.get(i));
        }
        return listResult;
    }

    public String toString (){
        StringBuilder builder = new StringBuilder();
        for ( List< Integer > list : xors ){
            for ( Integer number : list ){
                builder.append(( char ) number.intValue());
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public List< List< Integer > > getXors (){
        return xors;
    }

    public List< List< Integer > > getOrder (){
        return order;
    }

    public static List< List< Integer > > possibleKeys ( int startIndex, List< Integer > word1, int cipher1, int cipher2, MessageStorage messageStorage ){
        List< Integer > cipher1List = messageStorage.getCiphers().get(cipher1);
        List< Integer > cipher2List = messageStorage.getCiphers().get(cipher2);

        cipher1List = cipher1List.subList(startIndex, startIndex + word1.size());
        cipher2List = cipher2List.subList(startIndex, startIndex + word1.size());

        List< List< Integer > > possibleKeys = new ArrayList<>();
        List< Integer > possibleKey1 = new ArrayList<>();
        List< Integer > possibleKey2 = new ArrayList<>();
        for ( int i = 0; i < word1.size(); i++ ){
            possibleKey1.add(cipher1List.get(i) ^ word1.get(i));
            possibleKey2.add(cipher2List.get(i) ^ word1.get(i));
        }
        possibleKeys.add(possibleKey1);
        possibleKeys.add(possibleKey2);
        return possibleKeys;
    }

    public static void appedToKey ( int position, List< Integer > keyPart, List< Integer > key ){
        for ( int i = position, j = 0; j < keyPart.size(); j++, i++ ){
            key.set(i, keyPart.get(j));
        }
    }


}
