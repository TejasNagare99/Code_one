package com.excel.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordManager {
	
	private static final String[] PRIME_ARRAY= {"2","3","5","7","11","13","17","19","23","29","31","37","41","43","47","53","59","61","67","71","73","79","83","89","97","101","103","107","109","113","127","131","137","139"};
	private static final String PASSWORD_ENC_KEY = "$R#*$%UW#J*#%DGHF@#";
	private static final String PASSWORD_ENC_SALT = "25";
	
	// iteration count should be more for higher security but can take more time
	private static final int PASSWORD_ENC_ITERATION_COUNT = 10;
	
	// values other than 128 gives java.security.InvalidKeyException: Illegal key size or default parameter
	private static final int PASSWORD_ENC_KEY_LENGTH = 128;
	
    public static void main(String[] args) throws Exception {
		/*
		 * Scanner sc = new Scanner(System.in); System.out.print("Enter Password: ");
		 * String input = sc.next(); System.out.println(
		 * "--------------------------------------------------------------------------------"
		 * ); System.out.println("Encrypted Password: " + encrypt(encrypt(input)));
		 * System.out.println(
		 * "--------------------------------------------------------------------------------"
		 * ); sc.close();
		 */
    	
		
		  Scanner sc = new Scanner(System.in);
		  System.out.print("Enter Decrypted Password: "); String input = sc.next();
		  System.out.println(
		  "--------------------------------------------------------------------------------"
		  ); System.out.println("Encrypted Password: " + decrypt(decrypt(input)));
		  System.out.println(
		  "--------------------------------------------------------------------------------"
		  ); sc.close();
		 
		
		/*
		 * Scanner sc = new Scanner(System.in); System.out.print("Enter Password: ");
		 * String input = sc.next(); System.out.println(
		 * "--------------------------------------------------------------------------------"
		 * ); System.out.println("Encrypted Password: " + new
		 * BCryptPasswordEncoder().encode(input)); System.out.println(
		 * "--------------------------------------------------------------------------------"
		 * ); System.out.println("Encrypted Password: " + encrypt(encrypt(input)));
		 * 
		 * sc.close();
		 */
    }

    private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static String encrypt(String password) throws GeneralSecurityException, UnsupportedEncodingException {
    	byte[] salt = new String(PASSWORD_ENC_SALT).getBytes();
        SecretKeySpec key = createSecretKey(PASSWORD_ENC_KEY.toCharArray(), salt, PASSWORD_ENC_ITERATION_COUNT, PASSWORD_ENC_KEY_LENGTH);
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(password.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decrypt(String encPassword) throws GeneralSecurityException, IOException {
    	byte[] salt = new String(PASSWORD_ENC_SALT).getBytes();
        SecretKeySpec key = createSecretKey(PASSWORD_ENC_KEY.toCharArray(), salt, PASSWORD_ENC_ITERATION_COUNT, PASSWORD_ENC_KEY_LENGTH);
        String iv = encPassword.split(":")[0];
        String property = encPassword.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }
    
    //New Encryption
    public static String encrypt(String password,int saltval) throws GeneralSecurityException, UnsupportedEncodingException {
    	String salt_ = PRIME_ARRAY[saltval+3];
    	byte[] salt = new String(salt_).getBytes();
        SecretKeySpec key = createSecretKey(PASSWORD_ENC_KEY.toCharArray(), salt, PASSWORD_ENC_ITERATION_COUNT, PASSWORD_ENC_KEY_LENGTH);
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(password.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }
    
    public static String decrypt(String encPassword,int saltval) throws GeneralSecurityException, IOException {
    	String salt_ = PRIME_ARRAY[saltval+3];
    	byte[] salt = new String(salt_).getBytes();
        SecretKeySpec key = createSecretKey(PASSWORD_ENC_KEY.toCharArray(), salt, PASSWORD_ENC_ITERATION_COUNT, PASSWORD_ENC_KEY_LENGTH);
        String iv = encPassword.split(":")[0];
        String property = encPassword.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }
}