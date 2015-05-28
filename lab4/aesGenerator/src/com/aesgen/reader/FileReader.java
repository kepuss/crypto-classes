package com.aesgen.reader;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/6/15.
 */
public class FileReader{


    public static byte[] readBytesFromFile(String filePath){
        Path path = Paths.get(filePath);
        try{
            List<String> lines = Files.readAllLines(path);
            return DatatypeConverter.parseHexBinary(lines.get(0));
        } catch ( IOException e ){
            e.printStackTrace();
        }
        return null;
    }

    public static List<byte[]> readBytesLinesFromFile(String filePath){
        Path path = Paths.get(filePath);
        try{
            List<String> lines = Files.readAllLines(path);

            List<byte[]> byteLines = new ArrayList<>();
            lines.stream().forEach(e-> byteLines.add(DatatypeConverter.parseHexBinary(e)));
            return byteLines;
        } catch ( IOException e ){
            e.printStackTrace();
        }
        return null;
    }

    public static void saveBytesToFile(String filePath, byte[] message){
        Path path = Paths.get(filePath);
        List<String> lines = new ArrayList<>();
        try{
            lines.add(DatatypeConverter.printHexBinary(message));
            Files.write(path,lines);
        } catch ( IOException e ){
            e.printStackTrace();
        }
    }

    public static void saveBytesLinesToFile(String filePath, List<byte[]> messages){
        Path path = Paths.get(filePath);
        try{
  //          int i =0;
            List<String> lines = new ArrayList<>();
            messages.stream().forEach(e->lines.add(DatatypeConverter.printHexBinary(e)));
          //  for(byte[] line : messages){
//                path = Paths.get(filePath+Integer.toString(i));

                Files.write(path,lines);
    //            i++;
            //}
        } catch ( IOException e ){
            e.printStackTrace();
        }
    }

}
