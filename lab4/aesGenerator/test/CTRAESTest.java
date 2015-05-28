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
 *         Created by kepuss on 3/30/15.
 *
 *         Test data taken from http://www.inconteam.com/software-development/41-encryption/55-aes-test-vectors#aes-crt-128
 *
 */
public class CTRAESTest{
    private String AESCTRkey;
    private String AESCTRIV;
    private String AESCTRpaintext;
    private String AESCTRcipher;

    private String EMODE;
    private String WMODE;


    @Before
    public void init(){
        Security.addProvider(new BouncyCastleProvider());
        AESCTRkey = "2b7e151628aed2a6abf7158809cf4f3c";
        AESCTRIV = "f0f1f2f3f4f5f6f7f8f9fafbfcfdfeff";
        AESCTRpaintext = "6bc1bee22e409f96e93d7e117393172a";
        AESCTRcipher = "874d6191b620e3261bef6864990db6ce";
        EMODE = "OR";
        WMODE = "CTR";
    }

    @Test
    public void AESCTRTest() throws NoSuchSchemaException, NoSuchModeException{
        CipherSchema cipher = CipherSelector.select(WMODE);
        WorkMode mode = ModeSelector.select(EMODE);
        IVService ivService = new IVService();
        ivService.setIV(AESCTRIV);

        List<byte[]> byteList = new ArrayList<>();
        byteList.add(DatatypeConverter.parseHexBinary(AESCTRpaintext));

        List< byte[] > result = mode.decrypt(byteList, cipher, ivService, DatatypeConverter.parseHexBinary(AESCTRkey));

        Assert.assertEquals(AESCTRcipher.toUpperCase(), DatatypeConverter.printHexBinary(result.get(0)));

    }

}
