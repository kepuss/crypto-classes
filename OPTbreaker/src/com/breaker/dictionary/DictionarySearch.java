package com.breaker.dictionary;

import com.breaker.message.*;

import java.util.*;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/10/15.
 */
public class DictionarySearch{
    private List< String > dict;

    List< Integer > key;
    int percent;
    List< Integer > blackList;

    public DictionarySearch ( DictionaryService dictionaryService ){
        dict = dictionaryService.getWordList();
        Integer[] keyBeg = new Integer[ 250 ];
        Arrays.fill(keyBeg, - 1);
        key = new ArrayList< Integer >(Arrays.asList(keyBeg));
        blackList = new ArrayList<>();
    }

    public void search ( MessageStorage messageStorage, XorService xorService ){
        List< Integer > blackList = new ArrayList<>();
        for ( String word : dict ){

            if ( percent > 75 ){
                if ( askToBreak() ){
                    break;
                }
            }
            List< Integer > wordNumbers = DecodeService.stringToList(word);
            Map< List< Integer >, PossibleWords > suitedWordsMap = CribDraggingService.xorOneWordWithMessages(wordNumbers, xorService, messageStorage.getCiphers().size());
            addToKey(suitedWordsMap, messageStorage);
        }
    }

    private void addToKey ( Map< List< Integer >, PossibleWords > suitedWordsMap, MessageStorage messageStorage ){

        for ( Map.Entry< List< Integer >, PossibleWords > entry : suitedWordsMap.entrySet() ){
            checkPossibleKeys(entry.getValue(), entry.getKey(), messageStorage);
        }

    }

    private void checkPossibleKeys ( PossibleWords possibleWords, List< Integer > ciphersNumbers, MessageStorage messageStorage ){

        for ( Map.Entry< Integer, List< List< Integer > > > entry : possibleWords.getWords().entrySet() ){
            if ( ! ( isFullInBlackList(entry.getKey(), entry.getValue().get(0).size()) ) ){
                List< List< Integer > > possibleKeys = XorService.possibleKeys(entry.getKey(), entry.getValue().get(0), ciphersNumbers.get(0), ciphersNumbers.get(1), messageStorage);
                List< List< Integer > > decodedPart = DecodeService.decodePart(messageStorage.getCiphers(), possibleKeys.get(0), entry.getKey(), possibleKeys.get(0).size());
                if ( isDecodedPartLetters(decodedPart) ){
                    Integer[] keyBeg = new Integer[ 250 ];
                    Arrays.fill(keyBeg, - 1);
                    List< Integer > possibleKey = new ArrayList< Integer >(Arrays.asList(keyBeg));

                    XorService.appedToKey(entry.getKey(), possibleKeys.get(0), possibleKey);
                    System.out.format("%s", DecodeService.toString(DecodeService.decode(messageStorage.getCiphers(), possibleKey), entry.getKey(), possibleKeys.get(0).size()));


                    askAndWriteToKey(entry.getKey(), possibleKeys.get(0), entry.getValue().get(1), messageStorage.getMaxLength());
                    addToblackList(entry.getKey(), possibleKeys.get(0).size());

                }
            }

        }
//            decodedPart  = DecodeService.decodePart(messageStorage.getCiphers(),possibleKeys.get(1),entry.getKey(),possibleKeys.get(1).size());
//            if(isDecodedPartLetters(decodedPart)){
//                XorService.appedToKey(entry.getKey(),possibleKeys.get(1),key);
//                System.out.format("%s", DecodeService.toString(DecodeService.decode(messageStorage.getCiphers(), key), entry.getKey(), possibleKeys.get(0).size()));
//            }


    }

    private boolean isFullInBlackList ( int start, int length ){
        for ( int j = start; j < start + length; j++ ){
            if ( ! blackList.contains(j) ){
                return false;
            }
        }
        return true;
    }

    private void addToblackList ( int start, int length ){
        for ( int j = start; j < start + length; j++ ){
            blackList.add(Integer.valueOf(j));
        }
    }

    private void askAndWriteToKey ( int position, List< Integer > possibleKey, List< Integer > word, int maxLength ){
        System.out.format("\n\nSearched word: %s", DecodeService.listToString(word));
        System.out.format("\n\nPosition of occurence: %d", position);
        System.out.format("\nNumber of decripted positions of key: %d which is %d%%\nIs part correct? (t/n) ", decodedKey(), percent);
        Scanner reader = new Scanner(System.in);
        if ( reader.next().charAt(0) == 't' ){
            XorService.appedToKey(position, possibleKey, key);
            percent = ( int ) ( ( ( float ) decodedKey() / maxLength ) * 100 );
        }

    }

    private boolean askToBreak (){
        System.out.format("\nDo you want to end? (t/n) ");
        Scanner reader = new Scanner(System.in);
        if ( reader.next().charAt(0) == 't' ){
            return true;
        }
        return false;
    }

    private int decodedKey (){
        int counter = 0;
        for ( Integer keySymbol : key ){
            if ( keySymbol != - 1 ){
                counter++;
            }
        }
        return counter;
    }


    private boolean isDecodedPartLetters ( List< List< Integer > > decodedPart ){
        for ( List< Integer > line : decodedPart ){
            for ( Integer letter : line ){
                if ( ! ( ( letter > 64 && letter < 91 ) || ( letter > 96 && letter < 123 ) || letter == 32 || letter == 46 || letter == 44 ) ){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWordInDict ( List< Integer > word ){
        String wordString = DecodeService.listToString(word);
        for ( String dictWord : dict ){
            if ( wordString.contains(dictWord) ){
                return true;
            }
        }
        return false;
    }

    public List< Integer > getKey (){
        return key;
    }
}
