package com.aesgen;

import com.aesgen.cipher.CipherSchema;
import com.aesgen.cipher.CipherSelector;
import com.aesgen.cipher.IVService;
import com.aesgen.cipher.NoSuchSchemaException;
import com.aesgen.keystore.KeyStoreService;
import com.aesgen.modes.ModeSelector;
import com.aesgen.modes.NoSuchModeException;
import com.aesgen.modes.WorkMode;
import com.aesgen.reader.FileReader;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class DoMainArgs{
    @Option(name="-keyst", usage = "Key Store path")
    private String keyStorePath;

    @Option(name="-keypass", usage = "Key Store password")
    private String keyStorePass;

    @Option(name="-keyalias", usage = "Key alias")
    private String keyAlias;

    @Option(name="-Emode", usage = "Encryption mode")
    private String encryptionMode;

    @Option(name="-Wmode", usage = "Work mode")
    private String workMode;

    @Option(name="-in", usage = "Input file path")
    private String inputPath;

    private final String FILE_OUTPUT="output/message_output";


    public  void doMain(String[] args) throws IOException{
        CmdLineParser parser = new CmdLineParser(this);
        try{
            parser.parseArgument(args);
        } catch ( CmdLineException e ){
            e.printStackTrace();
        }


        try{
            CipherSchema cipher = null;
            cipher = CipherSelector.select(encryptionMode);
            WorkMode mode = ModeSelector.select(workMode);
            KeyStoreService keyStoreService = new KeyStoreService(keyStorePath, keyStorePass, keyAlias);
            IVService ivService = new IVService();
 //           ivService.setIV("f0f1f2f3f4f5f6f7f8f9fafbfcfdfeff");
            List< byte[] > result = mode.decrypt(FileReader.readBytesLinesFromFile(inputPath), cipher, ivService, DatatypeConverter.parseHexBinary("2b7e151628aed2a6abf7158809cf4f3c"));
            FileReader.saveBytesLinesToFile(FILE_OUTPUT+workMode, result);

        } catch ( NoSuchSchemaException e ){
            e.printStackTrace();
        } catch ( NoSuchModeException e ){
            e.printStackTrace();
        }
    }
}
