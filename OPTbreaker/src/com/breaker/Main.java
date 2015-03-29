package com.breaker;

import com.breaker.charsets.CustomCharset;
import com.breaker.dictionary.DictionarySearch;
import com.breaker.dictionary.DictionaryService;
import com.breaker.message.DecodeService;
import com.breaker.message.MessageStorage;
import com.breaker.message.XorService;
import com.breaker.reader.FileReader;

public class Main{

    public static void main ( String[] args ){
        FileReader fileReader = new FileReader("messages", CustomCharset.UTF8);
        MessageStorage messageStorage = new MessageStorage();
        messageStorage.setCiphers(fileReader.getContent());

        XorService xorService = new XorService(messageStorage);

        DictionaryService dictionaryService = new DictionaryService();
        DictionarySearch dictionarySearch = new DictionarySearch(dictionaryService);
        dictionarySearch.search(messageStorage, xorService);

        FileReader cipherFileReader = new FileReader("cipher", CustomCharset.UTF8);
        MessageStorage cipherMessageStorage = new MessageStorage();
        cipherMessageStorage.setCiphers(cipherFileReader.getContent());

        System.out.println(DecodeService.toString(DecodeService.decode(cipherMessageStorage.getCiphers(), dictionarySearch.getKey())));

    }
}
