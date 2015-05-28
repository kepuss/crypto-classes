import com.aesgen.cipher.CipherSchema;
import com.aesgen.cipher.CipherSelector;
import com.aesgen.cipher.IVService;
import com.aesgen.cipher.NoSuchSchemaException;
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
public class OFBAESTest{
    private String AESOFBkey;
    private String AESOFBIV;
    private String AESOFBpaintext;
    private String AESOFBcipher;

    private String EMODE;
    private String WMODE;


    @Before
    public void init(){
        Security.addProvider(new BouncyCastleProvider());
        AESOFBkey = "2b7e151628aed2a6abf7158809cf4f3c";
        AESOFBIV = "000102030405060708090A0B0C0D0E0F";
        AESOFBpaintext = "6bc1bee22e409f96e93d7e117393172a";
        AESOFBcipher = "3b3fd92eb72dad20333449f8e83cfb4a";
        EMODE = "OR";
        WMODE = "OFB";
    }

    @Test
    public void AESOFBTest() throws NoSuchSchemaException, NoSuchModeException{
        CipherSchema cipher = CipherSelector.select(WMODE);
        WorkMode mode = ModeSelector.select(EMODE);
        IVService ivService = new IVService();
        ivService.setIV(AESOFBIV);

        List<byte[]> byteList = new ArrayList<>();
        byteList.add(DatatypeConverter.parseHexBinary(AESOFBpaintext));

        List< byte[] > result = mode.decrypt(byteList, cipher, ivService, DatatypeConverter.parseHexBinary(AESOFBkey));

        Assert.assertEquals(AESOFBcipher.toUpperCase(), DatatypeConverter.printHexBinary(result.get(0)));

    }

}
