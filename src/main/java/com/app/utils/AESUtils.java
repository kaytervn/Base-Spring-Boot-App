package com.app.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AESUtils {
    static String SECRET_KEY = "cututusethayema1";
    static String AES = "AES";

    public static String encrypt(String input, boolean zipEnable) {
        return encrypt(SECRET_KEY, input, zipEnable);
    }

    public static String decrypt(String input, boolean zipEnable) {
        return decrypt(SECRET_KEY, input, zipEnable);
    }

    private static String encrypt(String encodeKey, String inputStr, boolean zipEnable) {
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encodeKey.getBytes(StandardCharsets.UTF_8), AES));
            byte[] outputBytes = cipher.doFinal(inputStr.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(zipEnable ? zip(outputBytes) : outputBytes);
        } catch (Exception ex) {
            log.error("Encryption failed", ex);
            return null;
        }
    }

    private static String decrypt(String encodeKey, String encryptedStr, boolean zipEnable) {
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encodeKey.getBytes(StandardCharsets.UTF_8), AES));
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedStr.getBytes(StandardCharsets.UTF_8));
            byte[] decryptedBytes = cipher.doFinal(zipEnable ? unzip(decodedBytes) : decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Decryption failed", e);
            return null;
        }
    }

    private static byte[] zip(byte[] input) throws Exception {
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        byte[] output = new byte[input.length];
        int compressedSize = deflater.deflate(output);
        deflater.end();
        return java.util.Arrays.copyOf(output, compressedSize);
    }

    private static byte[] unzip(byte[] input) throws Exception {
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        byte[] output = new byte[input.length * 2];
        int uncompressedSize = inflater.inflate(output);
        inflater.end();
        return java.util.Arrays.copyOf(output, uncompressedSize);
    }

    public static SecretKey generateAESKey(int keysize) {
        try {
            if (Cipher.getMaxAllowedKeyLength(AES) < keysize) {
                throw new InvalidParameterException("Key size of " + keysize + " not supported in this runtime");
            }
            KeyGenerator keyGen = KeyGenerator.getInstance(AES);
            keyGen.init(keysize);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            log.error("AES key generation failed", e);
            return null;
        }
    }

    public static SecretKey decodeBase64ToAESKey(String encodedKey) {
        byte[] keyData = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(keyData, AES);
    }

    public static String encodeAESKeyToBase64(SecretKey aesKey) {
        return Base64.getEncoder().encodeToString(aesKey.getEncoded());
    }
}
