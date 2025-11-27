package com.excel.security;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionService {
	
    private static byte[] key = "MZygpewJsCpRrfOr".getBytes(StandardCharsets.UTF_8);

    private static final String ALGORITHM = "AES";

    public static String encrypt(String plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Arrays.toString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decrypt(String cipherText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return Arrays.toString(cipher.doFinal(cipherText.getBytes(StandardCharsets.UTF_8)));
    }
    
    public static void main(String[] args) throws Exception {
    	String s = "SunilN@vetoquinol.com";
    	String e = encrypt(encrypt(s));
		System.out.println(e);
		System.out.println(decrypt(decrypt(s)));
	}
    
}
