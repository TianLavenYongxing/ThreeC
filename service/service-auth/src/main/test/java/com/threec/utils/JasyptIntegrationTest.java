package com.threec.utils;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableEncryptableProperties
public class JasyptIntegrationTest {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void testEncryption() {
        String originalMessage = "Hello ThreeC";
        String encrypted = encryptor.encrypt(originalMessage);
        String decrypted = encryptor.decrypt(encrypted);

        System.out.println("Original: " + originalMessage);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        assert originalMessage.equals(decrypted) : "Original and decrypted messages do not match!";
    }
}




