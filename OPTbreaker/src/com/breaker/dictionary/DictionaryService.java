package com.breaker.dictionary;

import com.breaker.charsets.CustomCharset;
import com.breaker.reader.FileReader;

import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/8/15.
 */
public class DictionaryService{
    private List< String > dict;
    private FileReader fileReader;
    private final String FILENAME = "dictionary_pl_small";

    public DictionaryService (){
        fileReader = new FileReader(FILENAME, CustomCharset.UTF8);
        dict = fileReader.getContent();
    }

    public List< String > getWordList (){
        return dict;
    }
}
