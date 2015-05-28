package com.vigenere;

import com.vigenere.logic.*;
import com.vigenere.reader.FileReader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

//        CODE USED IN PRETESTS
//        String testKey = "Dbecadloto";
//        String message ="The running key variant of the Vigenere cipher was also considered unbreakable at one time. This version uses as the key a block of text as long as the plaintext. Since the key is as long as the message the Friedman and Kasiski tests no longer work (the key is not repeated). In 1920, Friedman was the first to discover this variant's weaknesses. The problem with the running key Vigenere cipher is that the cryptanalyst has statistical information about the key (assuming that the block of text is in a known language) and that information will be reflected in the ciphertext.\n" +
//                "\n" +
//                "If using a key which is truly random, is at least as long as the encrypted message and is used only once, the Vigenere cipher is theoretically unbreakable. However, in this case it is the key, not the cipher, which provides cryptographic strength and such systems are properly referred to collectively as one-time pad systems, irrespective of which ciphers are employed.\n" +
//                "\n" +
//                "Vigenere actually invented a stronger cipher: an autokey cipher. The name \"Vigenere cipher\" became associated with a simpler polyalphabetic cipher instead. In fact, the two ciphers were often confused, and both were sometimes called \"le chiffre indechiffrable\". Babbage actually broke the much stronger autokey cipher, while Kasiski is generally credited with the first published solution to the fixed-key polyalphabetic ciphers.\n" +
//                "\n" +
//                "A simple variant is to encrypt using the Vigenere decryption method, and decrypt using Vigenere encryption. This method is sometimes referred to as \"Variant Beaufort\". This is different from the Beaufort cipher, created by Sir Francis Beaufort, which nonetheless is similar to Vigenere but uses a slightly modified enciphering mechanism and tableau. The Beaufort cipher is a reciprocal cipher.\n" +
//                "\n" +
//                "Despite the Vigenere cipher's apparent strength it never became widely used throughout Europe. The Gronsfeld cipher is a variant created by Count Gronsfeld which is identical to the Vigenere cipher, except that it uses just 10 different cipher alphabets (corresponding to the digits 0 to 9). The Gronsfeld cipher is strengthened because its key is not a word, but it is weakened because it has just 10 cipher alphabets. Gronsfeld's cipher did become widely used throughout Germany and Europe, despite its weaknesses.";
//
//
//        String cipher = encryptor.encrypt(testKey,message);
//        cipher = encryptor.encrypt(testKey,cipher);
//        System.out.print(cipher);




        Encryptor encryptor = new Encryptor();
        FileReader fileReader = new FileReader("message");
        String cipher = fileReader.getContentAscii();
        RepetitionFinder repetitionFinder = new RepetitionFinder();
        List<Integer> repetitions =   repetitionFinder.findRepetitions(fileReader.getContentAscii());

 //      List<Integer> repetitions = repetitionFinder.findRepetitions(cipher);
        System.out.println(repetitions.toString());
        FactorStatistic factorStatistic = new FactorStatistic();
        Map<Integer,Integer> statistics =  factorStatistic.calculateFactors(repetitions);
        List<Integer> possibleLength = factorStatistic.takeThreePossibleKeyLength(statistics);
        System.out.println(statistics.toString());
        System.out.println(possibleLength.toString());
        possibleLength = possibleLength.subList(0,8);


        List<List<String>> prepared = AnalysisPreparation.prepare(cipher, possibleLength);
       // System.out.println(preparedForAnalysis.toString());
        FrequencyAnalysis frequencyAnalysis = new FrequencyAnalysis();
        List<List<List<String>>> letters = frequencyAnalysis.analyseAll(prepared);

        System.out.println(letters.get(0).toString());
        System.out.println(letters.get(1).toString());
        System.out.println(letters.get(2).toString());
        System.out.println(letters.get(3).toString());
        System.out.println(letters.get(4).toString());
        System.out.println(letters.get(5).toString());
        System.out.println(letters.get(6).toString());

        KeyBuilder keyBuilder = new KeyBuilder();

        //The most possible key length 8
        String key = keyBuilder.buildFromFirst(letters.get(2));
        System.out.println("\nKey: " + key);
        System.out.println("Message: "+encryptor.encrypt(key, cipher));

        PrintWriter writer = null;
        try{
            writer = new PrintWriter("output.txt", "UTF-8");
        } catch ( FileNotFoundException e ){
            e.printStackTrace();
        } catch ( UnsupportedEncodingException e ){
            e.printStackTrace();
        }

        writer.println("\nKey: " + key);
        writer.println("Message: "+encryptor.encrypt(key, cipher));
        writer.close();
    }
}
