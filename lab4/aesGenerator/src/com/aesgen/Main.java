package com.aesgen;

import com.aesgen.cipher.AESCBC;
import com.aesgen.cipher.AESCTR;
import com.aesgen.cipher.AESOFB;
import com.aesgen.cipher.IVService;
import com.aesgen.keystore.KeyStoreService;
import com.aesgen.reader.FileReader;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        try{
            new DoMainArgs().doMain(args);
        } catch ( IOException e ){
            e.printStackTrace();
        }
    }


}
