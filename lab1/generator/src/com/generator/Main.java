package com.generator;

import com.generator.charsets.CustomCharset;
import com.generator.keystore.KeyStoreService;
import com.generator.reader.FileReader;
import com.generator.schemes.AESCTRScheme;
import com.generator.schemes.RC4Scheme;
import com.generator.schemes.Salsa20Scheme;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyStore;
import java.security.Security;

public class Main {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        FileReader fileReader = new FileReader(args[0], CustomCharset.UTF8);
        RC4Scheme rc4Scheme = new RC4Scheme();
        Salsa20Scheme salsa20Scheme = new Salsa20Scheme();
        AESCTRScheme aesctrScheme = new AESCTRScheme();


        System.out.print("RC4: ");
        System.out.println(rc4Scheme.cipher(fileReader.getContent(), CustomCharset.UTF8));
        System.out.print("Salsa20: ");
        System.out.println(salsa20Scheme.cipher(fileReader.getContent(), CustomCharset.UTF8));
        System.out.print("AES: ");
        System.out.println(aesctrScheme.cipher(fileReader.getContent(), CustomCharset.UTF8));

    }
}
