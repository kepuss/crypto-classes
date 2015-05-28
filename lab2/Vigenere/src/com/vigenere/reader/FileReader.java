package com.vigenere.reader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/6/15.
 */
public class FileReader{
    private  String  contentHex;
    private  String  contentDecimal;
    private  String  contentAscii;

    public FileReader ( String fileName ){
        File file = new File(fileName);
        try{
            contentHex = FileUtils.readFileToString(file);
        } catch ( IOException e ){
            e.printStackTrace();
        }
        transform();
    }

    private void transform (){
        contentHex = contentHex.replaceAll("\\s","");
        StringBuilder asciiSb = new StringBuilder();
        StringBuilder decimalSb = new StringBuilder();

        for( int i=0; i<contentHex.length()-1; i+=2 ){
            String output = contentHex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            asciiSb.append(( char ) decimal);
            decimalSb.append(decimal);
        }
        contentAscii = asciiSb.toString();
        contentDecimal = decimalSb.toString();
    }

    public String getContentHex (){
        return contentHex;
    }

    public String getContentDecimal (){
        return contentDecimal;
    }

    public String getContentAscii (){
        return contentAscii;
    }
}
