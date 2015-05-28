package com.breaker.message;

import java.util.*;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/8/15.
 */
public class CribDraggingService{

    public static Map< List< Integer >, PossibleWords > xorOneWordWithMessages ( List< Integer > word, XorService xorMessages, int messagesNumber ){
        Map< List< Integer >, PossibleWords > resultMap = new HashMap<>();
        Map< Integer, SuitedWords > suitedWordsMap = new HashMap<>();
        for ( int i = 0; i < xorMessages.getXors().size(); i++ ){
            resultMap.put(xorMessages.getOrder().get(i), xorOneWordWithString(word, xorMessages.getXors().get(i)));
        }
        return resultMap;
    }

    public static void findRepetingWords ( Map< Integer, SuitedWords > suitedWordsMap, Map< List< Integer >, PossibleWords > wordMap, int messagesNumber ){
        List< Integer > key;
        List< Integer > key2;
        for ( int i = 0; i < messagesNumber; i++ ){
            for ( int j = i + 1; j < messagesNumber - 2; j++ ){
                key = new ArrayList<>(Arrays.asList(i, j));
                PossibleWords possibleWords = wordMap.get(key);
                key2 = new ArrayList<>(Arrays.asList(i, j + 1));
                PossibleWords possibleWords2 = wordMap.get(key2);
                comparePossibleWords(suitedWordsMap, possibleWords, j, possibleWords2, j + 1, i);

            }
        }
    }

    public static void comparePossibleWords ( Map< Integer, SuitedWords > suitedWordsMap, PossibleWords pw1, int strNumber1, PossibleWords pw2, int strNumber2, int common ){
        Map< Integer, List< List< Integer > > > words1 = pw1.getWords();
        Map< Integer, List< List< Integer > > > words2 = pw2.getWords();

        words1.keySet().retainAll(words2.keySet());
        words2.keySet().retainAll(words1.keySet());

        Set< Integer > keySet = words1.keySet();

        SuitedWords suitedWordsCommon = suitedWordsMap.get(common);
        if ( suitedWordsCommon == null ){
            suitedWordsCommon = new SuitedWords();
        }
        SuitedWords suitedWordspw1 = suitedWordsMap.get(pw1);
        if ( suitedWordspw1 == null ){
            suitedWordspw1 = new SuitedWords();
        }
        SuitedWords suitedWordspw2 = suitedWordsMap.get(pw2);
        if ( suitedWordspw2 == null ){
            suitedWordspw2 = new SuitedWords();
        }

        for ( Integer key : keySet ){
            List< List< Integer > > words1ForPosition = words1.get(key);
            List< List< Integer > > words2ForPosition = words2.get(key);
            if ( words1ForPosition.get(1).equals(words2ForPosition.get(1)) ){
                suitedWordspw1.addWords(key, words1ForPosition.get(0));
                suitedWordsMap.put(strNumber1, suitedWordspw1);
                suitedWordspw2.addWords(key, words2ForPosition.get(0));
                suitedWordsMap.put(strNumber2, suitedWordspw2);
            }
        }
    }

    public static PossibleWords xorOneWordWithString ( List< Integer > word, List< Integer > message ){
        PossibleWords possibleWords = new PossibleWords();
        for ( int i = 0; i < message.size() - word.size() + 1; i++ ){
            List< Integer > subMessage = message.subList(i, i + word.size());
            List< Integer > xorResult = xorLists(word, subMessage);
            if ( isWord(xorResult) ){
                possibleWords.addWord(i, xorResult);
                possibleWords.addWord(i, word);
            }
        }
        return possibleWords;
    }

    public static List< Integer > xorLists ( List< Integer > word, List< Integer > message ){
        List< Integer > result = new ArrayList<>();
        for ( int i = 0; i < word.size(); i++ ){
            result.add(word.get(i) ^ message.get(i));
        }
        return result;
    }

    private static boolean isWord ( List< Integer > word ){
        for ( Integer number : word ){
            if ( ! ( ( number > 64 && number < 91 ) || ( number > 96 && number < 123 ) || number == 20 || number == 33 || number == 32 || number == 46 || number == 63 ) ){
                return false;
            }
        }
        return true;
    }
}
