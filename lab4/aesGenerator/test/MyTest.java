import com.aesgen.cipher.CipherSchema;
import com.aesgen.cipher.CipherSelector;
import com.aesgen.cipher.IVService;
import com.aesgen.cipher.NoSuchSchemaException;
import com.aesgen.keystore.KeyStoreService;
import com.aesgen.modes.ModeSelector;
import com.aesgen.modes.NoSuchModeException;
import com.aesgen.modes.WorkMode;
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
public class MyTest{
    private String AESCBCkey;
    private String AESCBCIV;
    private String AESCBCpaintext;
    private String AESCBCpaintext2;
    private String AESCBCcipher;

    private String EMODE;
    private String WMODE;


    @Before
    public void init(){
        Security.addProvider(new BouncyCastleProvider());
        AESCBCkey = "2b7e151628aed2a6abf7158809cf4f3c";
        AESCBCIV = "00000000000000000000000000000000";
        //AESCBCpaintext = "6bc1bee22e409f96e93d7e117393172a";
        AESCBCpaintext = "6bc1bee22e409f96e93d7e117393172a";
        AESCBCpaintext2 = "6bc1bee22e409f96e93d7e117393172b";
        AESCBCcipher = "7649abac8119b246cee98e9b12e9197d";
        EMODE = "CH";
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
        byteList.add(DatatypeConverter.parseHexBinary(AESCBCpaintext2));

        KeyStoreService keyStoreService = new KeyStoreService("resources/myKeyStore", "password", "secret");

        List< byte[] > results = mode.decrypt(byteList, cipher, ivService, keyStoreService.getKey());

        for(byte[] result : results){
            System.out.println(DatatypeConverter.printHexBinary(result));
        }
       // Assert.assertEquals(AESCBCcipher.toUpperCase(), DatatypeConverter.printHexBinary(results.get(0)));

    }

    @Test
    public void genKeyTest(){
        KeyStoreService keyStoreService = new KeyStoreService("resources/myKeyStore", "password","secret");
        keyStoreService.storeKey();

    }
}
