package com.app.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class GenerateUtils {
    private static final String SECRET_KEY = "kpE8wG5jEX";
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_INSTANCE = "AES/ECB/PKCS5Padding";

    private static SecretKeySpec getSecretKey() {
        try {
            byte[] key = MessageDigest.getInstance("SHA-1").digest(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(java.util.Arrays.copyOf(key, 16), ALGORITHM);
        } catch (Exception e) {
            log.error("Error generating secret key", e);
            return null;
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            return Base64.getUrlEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("Error encrypting string", e);
            return null;
        }
    }

    public static String decrypt(String strToDecrypt) {
        try {
            strToDecrypt = strToDecrypt.replace("/", "_").replace("+", "-");
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            return new String(cipher.doFinal(Base64.getUrlDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            log.error("Error decrypting string", e);
            return null;
        }
    }

    public static String generateRandomString(String prefix) {
        return generateRandomString(prefix, "yyyyMMddhhmmssssss");
    }

    public static String generateRandomStringSS(String prefix) {
        return generateRandomString(prefix, "yyyyMMddhhmmss");
    }

    private static String generateRandomString(String prefix, String dateFormat) {
        try {
            String result = new SimpleDateFormat(dateFormat).format(new Date());
            result += SecureRandom.getInstanceStrong().nextInt(9);
            return prefix == null ? result : prefix + "-" + result;
        } catch (Exception e) {
            log.error("Error generating random string", e);
            return null;
        }
    }

    public static String generateRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }
}
