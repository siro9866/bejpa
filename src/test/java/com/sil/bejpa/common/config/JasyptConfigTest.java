package com.sil.bejpa.common.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

//@SpringBootTest
@TestPropertySource(properties = "classpath=application-test.yml")
class JasyptConfigTest {

    @Value("${jasypt.encryptor.key}") String key;

    //@Test
    @DisplayName("비밀번호 암호화")
    public void passwordEncode(){
        String url = "jdbc:mariadb://localhost:3306/sil";
        String username = "sil";
        String password = "sil";
        String secret = "ThisIsSilFrameworkAndThisFrameworkIsBorn20250101BySil";

        System.out.println("key = " + key);
        System.out.println("url =" + jasyptEncoding(url));
        System.out.println("username =" + jasyptEncoding(username));
        System.out.println("password =" + jasyptEncoding(password));
        System.out.println("secret =" + jasyptEncoding(secret));
    }

    public String jasyptEncoding(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}
