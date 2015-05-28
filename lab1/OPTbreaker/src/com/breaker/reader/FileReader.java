package com.breaker.reader;

import com.breaker.charsets.CustomCharset;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/6/15.
 */
public class FileReader{
    private List< String > content;

    public FileReader ( String fileName, CustomCharset charset ){
        File file = new File(fileName);
        try{
            content = FileUtils.readLines(file);
        } catch ( IOException e ){
            e.printStackTrace();
        }
    }

    public List< String > getContent (){
        return content;
    }

}
