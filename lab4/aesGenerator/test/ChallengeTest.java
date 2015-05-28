import com.aesgen.cipher.CipherSchema;
import com.aesgen.cipher.CipherSelector;
import com.aesgen.cipher.IVService;
import com.aesgen.cipher.NoSuchSchemaException;
import com.aesgen.keystore.KeyStoreService;
import com.aesgen.modes.ModeSelector;
import com.aesgen.modes.NoSuchModeException;
import com.aesgen.modes.WorkMode;
import com.aesgen.reader.FileReader;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 4/2/15.
 */
public class ChallengeTest{
    private String AESCBCpaintext1;
    private String AESCBCpaintext2;
    private String AESCBCpaintext3;

    private String callString = "/usr/lib/jvm/java-8-oracle/bin/java -Didea.launcher.port=7537 -Didea.launcher.bin.path=/home/kepuss/idea/bin -Dfile.encoding=UTF-8 -classpath /usr/lib/jvm/java-8-oracle/jre/lib/jfxswt.jar:/usr/lib/jvm/java-8-oracle/jre/lib/deploy.jar:/usr/lib/jvm/java-8-oracle/jre/lib/resources.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jfr.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jsse.jar:/usr/lib/jvm/java-8-oracle/jre/lib/management-agent.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jce.jar:/usr/lib/jvm/java-8-oracle/jre/lib/javaws.jar:/usr/lib/jvm/java-8-oracle/jre/lib/plugin.jar:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/lib/charsets.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/sunec.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/localedata.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/jfxrt.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/zipfs.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/nashorn.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/cldrdata.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/sunjce_provider.jar:/home/kepuss/IdeaProjects/aesGenerator/out/production/aesGenerator:/home/kepuss/Downloads/bcprov-debug-jdk15on-152.jar:/home/kepuss/Downloads/junit-4.12.jar:/home/kepuss/Downloads/hamcrest-core-1.3.jar:/home/kepuss/Downloads/args4j-2.32.jar:/home/kepuss/idea/lib/idea_rt.jar com.intellij.rt.execution.application.AppMain com.aesgen.Main ";
    private String challengeParamString = " -keyst resources/myKeyStore -keypass password -keyalias secret -Emode CBC -Wmode CH -in input/messageCH";
    private String oracleParamString =" -keyst resources/myKeyStore -keypass password -keyalias secret -Emode CBC -Wmode OR -in input/messageOR";

    @Before
    public void init(){

        AESCBCpaintext1 = "6bc1bee22e409f96e93d7e1173931720";
        AESCBCpaintext2 = "6bc1bee22e409f96e93d7e1173931721";
        AESCBCpaintext3 = "2b7e151628aed2a6abf7158809cf4f3c";

        List<byte[]> list = new ArrayList<>();
        list.add(DatatypeConverter.parseHexBinary(AESCBCpaintext1));
        FileReader.saveBytesLinesToFile("input/messageOR",list);


        list = new ArrayList<>();
        list.add(DatatypeConverter.parseHexBinary(AESCBCpaintext2));
        list.add(DatatypeConverter.parseHexBinary(AESCBCpaintext3));
        FileReader.saveBytesLinesToFile("input/messageCH",list);
    }

    @Test
    public void AESCBCTest() throws NoSuchSchemaException, NoSuchModeException, IOException, InterruptedException{

        Runtime rt = Runtime.getRuntime();
        String a = callString+challengeParamString;

        Process prOr = rt.exec(callString+oracleParamString);
        prOr.waitFor();
        String outputOracle = DatatypeConverter.printHexBinary(FileReader.readBytesFromFile("output/message_outputOR"));

        Process prCh = rt.exec(callString+challengeParamString);
        prCh.waitFor();
        String outputChallenge = DatatypeConverter.printHexBinary(FileReader.readBytesFromFile("output/message_outputCH"));

        if(outputOracle.equals(outputChallenge)){
            System.out.print("b = 0");
        }else{
            System.out.print("b = 1");
        }

    }
}
