package com.generator.reader;

import com.generator.charsets.CustomCharset;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/6/15.
 */
public class FileReader{
    private String content;

    public FileReader(String fileName,CustomCharset charset){
        File file = new File(fileName);
        try{
            content = FileUtils.readFileToString(file, Charset.forName(charset.toString()));
        } catch ( IOException e ){
            e.printStackTrace();
        }
    }

    public String getContent (){
        return content;
    }

    public void setContent ( String content ){
        this.content = content;
    }
}
