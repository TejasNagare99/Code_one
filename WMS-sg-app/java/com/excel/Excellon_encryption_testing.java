package com.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


public class Excellon_encryption_testing {

	private static String EXACT_AUTH_URL_DEV = "https://demoapiewb.exactgst.com/api/authentication/getAuthenticationToken";
	private static String EXACT_ACCESS_TOKEN_URL_DEV = "https://demoapiewb.exactgst.com/ewaybillapi/v1.03/Auth";
	private static String EXACT_SUBSCRIPTION_ID = "f617ddc2-6c73-45b6-b551-f0536985e43c";

	private static String EXACT_PORTAL_CLIENT_ID = "e49dcadf-2574-4cd1-86d5-8ceb110d7899";
	private static String EXACT_PORTAL_CLIENT_SECRET = "debaef8545d6405e995323e5531ffeb6";
	private static String EXACT_PUBLIC_KEY_CERTIFICTAE_PATH = "D:\\SAMRUDDHA\\CFA_STOCKIST APP\\EWBRestful_04012024\\Public Keys\\Exact Public Key Certificate_16092021\\exactgst.in_16092021.crt";
	
	private static String NIC_PUBLIC_KEY_PEM_PATH = "D:\\SAMRUDDHA\\CFA_STOCKIST APP\\EWBRestful_04012024\\Public Keys\\PublicKey\\einv_sandbox.pem";
	private static String NIC_PUBLIC_KEY_FOR_EWB_PEM_PATH = "D:\\SAMRUDDHA\\CFA_STOCKIST APP\\EWBRestful_04012024\\Public Keys\\Sandbox_NIC_OLD_publickey\\certificate_publickey.pem";

	
	private static RestTemplate restTemplate = new RestTemplate();
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private static byte[] appKey_btArr;
	

	public static byte[] constructor(String encrypt_input, String certificate_path) throws Exception {
		byte[] secretMessageBytes = encrypt_input.getBytes(StandardCharsets.UTF_8);
		return constructor(secretMessageBytes,certificate_path);
	}

	public static byte[] constructor(byte[] encrypt_input, String certificate_path) throws Exception {
		FileInputStream fin = new FileInputStream(certificate_path);
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);
		PublicKey publicKey = certificate.getPublicKey();
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return encryptCipher.doFinal(encrypt_input);
	}

	public static void main(String[] args) throws Exception {
		//authenticate_excellon_user();
		//gen_auth_token_gst_user();
		gen_auth_token_gst_user_for_ewb();
	}

	public static void authenticate_excellon_user() throws Exception {
		// encode the client secret using the exact public key
		byte[] encryptedMessageBytes = constructor(EXACT_PORTAL_CLIENT_SECRET, EXACT_PUBLIC_KEY_CERTIFICTAE_PATH);
		String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

		auth_req_body_pojo req_body = new auth_req_body_pojo(EXACT_PORTAL_CLIENT_ID, encodedMessage);
		System.out.println(new Gson().toJson(req_body).toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<auth_req_body_pojo> request = new HttpEntity<>(req_body, headers);

		//String res = restTemplate.postForObject(EXACT_AUTH_URL_DEV, request, String.class);
		
		//new logic
		ResponseEntity<String> resp_ent = 
		        restTemplate.exchange(EXACT_AUTH_URL_DEV, 
		                              HttpMethod.POST, request,String.class);
		
		if(!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			throw new Exception("HTTP Error Code :" + resp_ent.getStatusCodeValue());
		}
		
		String responseText = resp_ent.getBody();
        System.out.println("Response:" + responseText);
        
        JsonNode tree = objectMapper.readTree(responseText);
        Boolean isAuthenticated = tree.get("IsAuthenticated").asBoolean();
        
        
        if(isAuthenticated) {
        	String AuthenticationToken = tree.get("AuthenticationToken").asText();
        	String AuthenticationValidTillDateTime = tree.get("AuthenticationValidTillDateTime").asText();
        	LocalDateTime dateTime = LocalDateTime.parse(AuthenticationValidTillDateTime);
        	JsonNode array = tree.get("AuthorizedSubscriptions");
        	JsonNode subs = array.get(0);
        	String subscriptionId = subs.get("SubscriptionId").asText();
        }
        else {
        	String errCode = tree.get("ErrorNumber").asText();
        	String errMsg = tree.get("ErrorMessage").asText();
        	System.out.println("Error with code : "+errCode+"\n Error Message : "+errMsg);
        	throw new Exception("Failed to Authenticate.Please try again.");
        }
	}
	
	public static void gen_auth_token_gst_user_for_ewb() throws Exception{
		String password = "123@Excellon";
		String username = "Excellon";
		String gst_in = "29AAYPT1804B000";
		
		String appKey = Base64.getEncoder().encodeToString(createAESKey());

		//create pojo
		String payload = "{\"action\":\"ACCESSTOKEN\",\"username\":\"" + username + "\",\"password\":\""
		+ password + "\",\"app_key\":\"" + appKey + "\"}";
		
		System.out.println("Payload: Plain: " + payload);

	    payload = Base64.getEncoder().encodeToString(payload.getBytes());
	    payload = "{\"Data\":\""+encryptAsymmentricKey(payload)+"\"}";
	    System.out.println("Final PayLoad :: " + payload);

		
		// Create Http request object with headers and payload to consume authentication API
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("AuthenticationToken",
						"A6C40AA9513697EE91DF956FAED72869110232617D3E9531F3C6710B3BE72A37AA4C962989509BDE6BD82AFDCB9C5DB96DC9F8BB0E15EEC3436299CBB9B0763A5C4B898E5610695291496808CFCF197B29E31307AE4F4AE2353BC8047EF9A114DB8E7F056373325335C7DD0C37AFF1291A4D2D91CA9E56005774514D64FEB6E1A2CCC4FD3FA69A7171D25D63EF1B11719FE3CAE122869C6158E9C3CCFCBA590E7AAD520FC198455C2AC4315DC06F1658BF82EB57E46F740F92A9342F894AE44ABF0A644A6A3FB02A1C07240858A6461980FDBBB751C3856B41B01E3A8D690F48");
				headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);
				headers.set("gstin", gst_in);

				HttpEntity<String> request = new HttpEntity<>(payload, headers);
				//Object res = restTemplate.postForObject(EXACT_ACCESS_TOKEN_URL_DEV, request,Object.class);
				
				ResponseEntity<String> resp_ent = 
				        restTemplate.exchange(EXACT_ACCESS_TOKEN_URL_DEV, 
				                              HttpMethod.POST, request,String.class);
				
				if(!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
					throw new RuntimeException("Failed : HTTP error code : " + resp_ent.getStatusCode());
				}
				
				
		        String responseText = resp_ent.getBody();
		        System.out.println("Response:" + responseText);
		        String status = objectMapper.readTree(responseText).get("status").asText();
		        
		        if (status.equals("0")) {
		            String errorDesc = "";
		            errorDesc = objectMapper.readTree(responseText).get("error").asText();
		            System.out.println("Error: " + errorDesc);
		        }
		        if (status.equals("1")) {
		            String authtoken = objectMapper.readTree(responseText).get("Data").get("AuthToken").asText();
		            String sek = objectMapper.readTree(responseText).get("Data").get("Sek").asText();
		            System.out.println("Authtoken: " + authtoken);
		            System.out.println("Encrypted SEK: " + sek);
		            sek = decrptBySymmetricKeySEK(appKey,sek);
		            System.out.println("Decrypted SEK: " + sek);
		        }
				
				System.out.println(resp_ent.getBody().toString());
		
	}

	public static void gen_auth_token_gst_user() throws Exception {
		// String authorize_token = "";
		String password = "123@Excellon";
		String username = "Excellon";
		String gst_in = "29AAYPT1804B000";
		
		String appKey = Base64.getEncoder().encodeToString(createAESKey());
		//create payload
		 String payload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"appkey\":\"" + appKey + "\",\"ForceRefreshAccessToken\": true}";
         System.out.println("Payload: Plain: " + payload);
         payload = Base64.getEncoder().encodeToString(payload.getBytes());
         payload = "{\"Data\":\"" + encryptAsymmentricKey(payload) + "\"}";
         System.out.println("Payload: Encrypted: " + payload);
		
		
		// Create Http request object with headers and payload to consume authentication API
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("AuthenticationToken",
				"5BDE5605967B1AD154AC5A7FEE40A1036ED2A675216F2DE0517762ADA102BA9E800ABEE9893DBD2F1CF6AE648A886DABBD3B816D37BE4F5E9A83E577B51B68BE9B7B2DDAB587633DBFEBF042E945FF40158DD40E69BE37496483D34E80C3266E6C5B64F6C838A748C79B21AABF1F13AF40C6A3DEDDAB4A3D42FC1623BD0A070C870F50044312F1731A1C25C2E91E80C3794AA4699E900A56DA97FD367B6B431545A5AA84A8E3CC9217B7C5E4B970FC457417EE8FBAAEC4FA4AAB67D9946FA6FB0A572DDECD82694327C5635D7AFB5B1B02684275AC909E24546BD822FECD0E4B");
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);
		headers.set("gstin", gst_in);

		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		//Object res = restTemplate.postForObject(EXACT_ACCESS_TOKEN_URL_DEV, request,Object.class);
		
		ResponseEntity<String> resp_ent = 
		        restTemplate.exchange(EXACT_ACCESS_TOKEN_URL_DEV, 
		                              HttpMethod.POST, request,String.class);
		
		if(!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			throw new RuntimeException("Failed : HTTP error code : " + resp_ent.getStatusCode());
		}
		
		
        String responseText = resp_ent.getBody();
        System.out.println("Response:" + responseText);
        String status = objectMapper.readTree(responseText).get("Status").asText();
        
        if (status.equals("0")) {
            String errorDesc = "";
            errorDesc = objectMapper.readTree(responseText).get("error").asText();
            System.out.println("Error: " + errorDesc);
        }
        if (status.equals("1")) {
            String authtoken = objectMapper.readTree(responseText).get("Data").get("AuthToken").asText();
            String sek = objectMapper.readTree(responseText).get("Data").get("Sek").asText();
            System.out.println("Authtoken: " + authtoken);
            System.out.println("Encrypted SEK: " + sek);
            sek = decrptBySymmetricKeySEK(appKey,sek);
            System.out.println("Decrypted SEK: " + sek);
        }
		
		System.out.println(resp_ent.getBody().toString());

	}
	
	 public static String decrptBySymmetricKeySEK(String appKey,String encryptedSek) {
		 	byte[] decoded = Base64.getDecoder().decode(appKey);
		 	
	        Key aesKey = new SecretKeySpec(decoded, "AES"); // converts bytes(32 byte random generated) to key
	        try {
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  // encryption type = AES with padding PKCS5
	            cipher.init(Cipher.DECRYPT_MODE, aesKey); // initiate decryption type with the key
	            byte[] encryptedSekBytes = Base64.getDecoder().decode(encryptedSek); // decode the base64 encryptedSek to bytes
	            byte[] decryptedSekBytes = cipher.doFinal(encryptedSekBytes); // decrypt the encryptedSek with the initialized cipher containing the key(Results in bytes)
	            byte[] sekBytes = decryptedSekBytes;
	            String decryptedSek = Base64.getEncoder().encodeToString(decryptedSekBytes); // convert the decryptedSek(bytes) to Base64 String
	            return decryptedSek;  // return results in base64 string
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return "Exception; " + e;
	        }
	    }
	
	public static PublicKey getPublicKey() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        FileInputStream in = new FileInputStream(NIC_PUBLIC_KEY_FOR_EWB_PEM_PATH);
        byte[] keyBytes = new byte[in.available()];
        in.read(keyBytes);
        in.close();
        String pubKey = new String(keyBytes, "UTF-8");
        pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        keyBytes = Base64.getMimeDecoder().decode(pubKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }
	
	
	private static String encryptAsymmentricKey(String clearText) throws Exception {
        PublicKey publicKeys = getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, publicKeys);
        byte[] encryptedText = cipher.doFinal(clearText.getBytes());
        String encryptedPassword = Base64.getEncoder().encodeToString(encryptedText);
        return encryptedPassword;
    }
	
	
	
	public static byte[] createAESKey() throws NoSuchAlgorithmException {
        try {
            KeyGenerator gen = KeyGenerator.getInstance("AES");
            gen.init(128);
            SecretKey secret = gen.generateKey();
            appKey_btArr = secret.getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw ex;
        }
        return appKey_btArr;
    }

	public static SecretKey generateKey(String algorithm, int key_length) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		keyGenerator.init(key_length);
		SecretKey key = keyGenerator.generateKey();
		return key;
	}

	public static class Payload_Data_object {
		private String Data;

		public String getData() {
			return Data;
		}

		public void setData(String data) {
			Data = data;
		}

		public Payload_Data_object(String data) {
			super();
			Data = data;
		}

		@Override
		public String toString() {
			return "Payload_Data_object [Data=" + Data + "]";
		}

	}

	public static class access_token_req_body {
		private boolean ForceRefreshAccessToken;
		private String UserName;
		private String app_key;
		private String password;
		
		@Override
		public String toString() {
			return "access_token_req_body [ForceRefreshAccessToken=" + ForceRefreshAccessToken + ", UserName="
					+ UserName + ", app_key=" + app_key + ", password=" + password + "]";
		}
		public boolean getForceRefreshAccessToken() {
			return ForceRefreshAccessToken;
		}
		public void setForceRefreshAccessToken(boolean forceRefreshAccessToken) {
			ForceRefreshAccessToken = forceRefreshAccessToken;
		}
		public String getUserName() {
			return UserName;
		}
		public void setUserName(String userName) {
			UserName = userName;
		}
		public String getApp_key() {
			return app_key;
		}
		public void setApp_key(String app_key) {
			this.app_key = app_key;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public access_token_req_body(boolean forceRefreshAccessToken, String userName, String app_key, String password) {
			super();
			ForceRefreshAccessToken = forceRefreshAccessToken;
			UserName = userName;
			this.app_key = app_key;
			this.password = password;
		}
		
		

		
	}

	public static class auth_req_body_pojo {
		private String ClientId;
		private String ClientSecret;

		public String getClientId() {
			return ClientId;
		}

		public void setClientId(String clientId) {
			ClientId = clientId;
		}

		public String getClientSecret() {
			return ClientSecret;
		}

		public void setClientSecret(String clientSecret) {
			ClientSecret = clientSecret;
		}

		public auth_req_body_pojo(String clientId, String clientSecret) {
			super();
			ClientId = clientId;
			ClientSecret = clientSecret;
		}

		@Override
		public String toString() {
			return "auth_req_body_pojo [ClientId=" + ClientId + ", ClientSecret=" + ClientSecret + "]";
		}

	}

	// AES symmetric key encryption
	private static String encryptBySymmetricKey(String json, String decryptedSek) {
		byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
		Key aesKey = new SecretKeySpec(sekByte, "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encryptedjsonbytes = cipher.doFinal(json.getBytes());
			String encryptedJson = Base64.getEncoder().encodeToString(encryptedjsonbytes);
			return encryptedJson;
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception " + e;

		}
	}

	// AES symmetric key decryption
	public static String decrptyBySyymetricKey(String encryptedSek, byte[] appKey) {
		Key aesKey = new SecretKeySpec(appKey, "AES"); // converts bytes(32 byte random generated) to key
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // encryption type = AES with padding PKCS5
			cipher.init(Cipher.DECRYPT_MODE, aesKey); // initiate decryption type with the key
			byte[] encryptedSekBytes = Base64.getDecoder().decode(encryptedSek); // decode the base64 encryptedSek to
																					// bytes
			byte[] decryptedSekBytes = cipher.doFinal(encryptedSekBytes); // decrypt the encryptedSek with the
																			// initialized cipher containing the
																			// key(Results in bytes)
			String decryptedSek = Base64.getEncoder().encodeToString(decryptedSekBytes); // convert the
																							// decryptedSek(bytes) to
																							// Base64 StriNG
			return decryptedSek; // return results in base64 string
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception; " + e;
		}
	}

}
