import com.aesgen.cipher.*;
import com.aesgen.modes.ModeSelector;
import com.aesgen.modes.NoSuchModeException;
import com.aesgen.modes.WorkMode;
import com.aesgen.reader.FileReader;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 4/2/15.
 */
public class CBCAESTest{
    private String AESCBCkey;
    private String AESCBCIV;
    private String AESCBCpaintext;
    private String AESCBCcipher;

    private String EMODE;
    private String WMODE;


    @Before
    public void init(){
        Security.addProvider(new BouncyCastleProvider());
        AESCBCkey = "2b7e151628aed2a6abf7158809cf4f3c";
        AESCBCIV = "000102030405060708090A0B0C0D0E0F";
        AESCBCpaintext = "6bc1bee22e409f96e93d7e117393172a";
        AESCBCcipher = "7649abac8119b246cee98e9b12e9197d";
        EMODE = "OR";
        WMODE = "CBC";
    }

    @Test
    public void AESCBCTest() throws NoSuchSchemaException, NoSuchModeException{
        CipherSchema cipher = CipherSelector.select(WMODE);
        WorkMode mode = ModeSelector.select(EMODE);
        IVService ivService = new IVService();
        ivService.setIV(AESCBCIV);

        List<byte[]> byteList = new ArrayList<>();
        byteList.add(DatatypeConverter.parseHexBinary(AESCBCpaintext));

        List< byte[] > result = mode.decrypt(byteList, cipher, ivService, DatatypeConverter.parseHexBinary(AESCBCkey));

        Assert.assertEquals(AESCBCcipher.toUpperCase(), DatatypeConverter.printHexBinary(result.get(0)));

    }

    @Test
    public void AESCBCTestFromRsa() throws NoSuchSchemaException, NoSuchModeException{
        CipherSchema cipher = CipherSelector.select(WMODE);
        WorkMode mode = ModeSelector.select(EMODE);
        IVService ivService = new IVService();
        ivService.setIV(AESCBCIV);

        List<byte[]> byteList = FileReader.readBytesLinesFromFile("input/messages10");
        //byteList.add(DatatypeConverter.parseHexBinary(AESCBCpaintext));
        AESCBC aes = new AESCBC();
        long startTime = System.nanoTime();
        for(int i =0;i<byteList.size();i++) {
            aes.encrypt(DatatypeConverter.parseHexBinary(AESCBCkey), byteList.get(0), ivService.getIV());
        }
        long endTime = System.nanoTime();
        System.out.println( "time: "+(endTime - startTime));
     //   System.out.println(DatatypeConverter.printHexBinary(result));

              //  List< byte[] > result = mode.decrypt(byteList, cipher, ivService, DatatypeConverter.parseHexBinary(AESCBCkey));

        //Assert.assertEquals(AESCBCcipher.toUpperCase(), DatatypeConverter.printHexBinary(result.get(0)));

    }

}
