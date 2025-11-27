package com.excel.service;

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
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.excel.Excellon_encryption_testing.auth_req_body_pojo;
import com.excel.bean.EInvoiceByIrnData;
import com.excel.bean.EInvoiceCancelData;
import com.excel.bean.EInvoiceData;
import com.excel.bean.ExcellonGenIrnResponse;
import com.excel.model.Dispatch_Detail;
import com.excel.model.Dispatch_Detail_Arc;
import com.excel.model.EInvoiceHeader;
import com.excel.model.EInvoiceHeaderStockist;
import com.excel.model.EInvoiceHeader_Arc;
import com.excel.model.EInvoiceHeader_Arc_Stockist;
import com.excel.model.Sum_Disp_Detail;
import com.excel.model.Sum_Disp_Detail_Arc;
import com.excel.repository.DispatchRepository;
import com.excel.repository.EInvoiceRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.restcontroller.EInvoiceExcellonController;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

@Service

public class ExcellonEInvoiceService implements MedicoConstants {

	@Autowired
	EInvoiceRepository einvoicerepo;
	@Autowired
	DispatchRepository dispatchrepository;
	@Autowired
	TransactionalRepository transactionalRepository;

//	@Value("${spring.profiles.active}") // Injecting the value of app.greeting from application.properties
//	private String profile;
	
	@Autowired
    private Environment environment;

	private static final Logger logger = LogManager.getLogger(ExcellonEInvoiceService.class);

	// ---------------------------------constants |
	// URIs-------------------------------------------//

	private static final String EXACT_AUTH_URL_DEV = "/api/authentication/getAuthenticationToken";
	private static final String EXACT_ACCESS_TOKEN_URL_DEV = "/eivital/v1.04/auth";

//	private static final String EXACT_SUBSCRIPTION_ID = "1446012b-d4ba-46da-8dcb-82ad24b6ddb0";
//	private static final String EXACT_PORTAL_CLIENT_ID = "5b7dd165-9913-4fc1-961a-a50c4f504c6e";
//	private static final String EXACT_PORTAL_CLIENT_SECRET = "077bbd26af584fdab6e232fef16c65b8";
//	private static String EXACT_PUBLIC_KEY_CERTIFICTAE_PATH = "D:\\SAMRUDDHA\\CFA_STOCKIST APP\\EWBRestful_04012024\\Public Keys\\Exact Public Key Certificate_16092021\\exactgst.in_16092021.crt";
//	private static String NIC_PUBLIC_KEY_PEM_PATH = "D:\\SAMRUDDHA\\CFA_STOCKIST APP\\EWBRestful_04012024\\Public Keys\\PublicKey\\einv_sandbox.pem";

	private static String EXACT_SUBSCRIPTION_ID;
	private static String EXACT_PORTAL_CLIENT_ID;
	private static String EXACT_PORTAL_CLIENT_SECRET;
	private static String EXACT_PUBLIC_KEY_CERTIFICTAE_PATH;
	private static String NIC_PUBLIC_KEY_PEM_PATH;

	private static final String HOST_FOR_EWB = "https://demoapiewb.exactgst.com";
	private static final String EXACT_AUTH_URL_DEV_FOR_EWB = "/api/authentication/getAuthenticationToken";
	private static final String EXACT_ACCESS_TOKEN_FOR_EWB_URL_DEV = "/ewaybillapi/v1.03/Auth";
	private static final String EXACT_SUBSCRIPTION_ID_FOR_EWAYBILL = "f617ddc2-6c73-45b6-b551-f0536985e43c";
	private static final String EXACT_PORTAL_CLIENT_ID_FOR_EWB = "e49dcadf-2574-4cd1-86d5-8ceb110d7899";
	private static final String EXACT_PORTAL_CLIENT_SECRET_FOR_EWB = "debaef8545d6405e995323e5531ffeb6";
	private static final String NIC_PUBLIC_KEY_FOR_EWB_PEM_PATH = "D:\\SAMRUDDHA\\CFA_STOCKIST APP\\EWBRestful_04012024\\Public Keys\\Sandbox_NIC_OLD_publickey\\certificate_publickey.pem";

	private static RestTemplate restTemplate = new RestTemplate();
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static byte[] appKey_btArr;

	private static final String GET_IRN_DETAILS_BY_DOC_DTLS = "/eicore/v1.03/Invoice/irnbydocdetails";
	private static final String GET_EWB_BY_IRN = "/eiewb/v1.03/ewaybill/";

	// ------------------------------------authorize excellon
	// user---------------------------------------//

	// @PostConstruct ensures that profile is injected before init() is called,
	// avoiding NullPointerException.
	@PostConstruct
	public void init() {
		String result = environment.getProperty("spring.profiles.active");
		if (result.equals("stkprod")) {
			System.out.println("IS PROD from EXCELLON");
			EXACT_SUBSCRIPTION_ID = "d219fd47-587f-4378-bea2-94ee1189db6c";
			EXACT_PORTAL_CLIENT_ID = "1a2cd039-e436-4add-b255-36f81723d120";
			EXACT_PORTAL_CLIENT_SECRET = "0c543053c23a431fa99f0b98e36f1b0e";
			EXACT_PUBLIC_KEY_CERTIFICTAE_PATH = "D:\\EXCELLON_EINV_DOCS\\EINVRestful_04001024\\Public keys\\Exact Public Key Certificate_16092021\\exactgst.in_16092021.crt";
			NIC_PUBLIC_KEY_PEM_PATH = "D:\\EXCELLON_EINV_DOCS\\EINVRestful_04001024\\Public keys\\certificate_publickey.pem";
		} else {
			System.out.println("IS DEV from EXCELLON");
			EXACT_SUBSCRIPTION_ID = "1446012b-d4ba-46da-8dcb-82ad24b6ddb0";
			EXACT_PORTAL_CLIENT_ID = "5b7dd165-9913-4fc1-961a-a50c4f504c6e";
			EXACT_PORTAL_CLIENT_SECRET = "077bbd26af584fdab6e232fef16c65b8";
			EXACT_PUBLIC_KEY_CERTIFICTAE_PATH = "D:\\SAMRUDDHA\\CFA_STOCKIST APP\\EINVRestful_04001024\\Public Keys\\Exact Public Key Certificate_16092021\\exactgst.in_16092021.crt";
			NIC_PUBLIC_KEY_PEM_PATH = "D:\\SAMRUDDHA\\CFA_STOCKIST APP\\EINVRestful_04001024\\Public Keys\\PublicKey\\einv_sandbox.pem";
		}
	}

	private static byte[] RsaEncryptExcellonPubKey(String encrypt_input, String certificate_path) throws Exception {
		byte[] secretMessageBytes = encrypt_input.getBytes(StandardCharsets.UTF_8);
		return RsaEncryptExcellonPubKey(secretMessageBytes, certificate_path);
	}

	private static byte[] RsaEncryptExcellonPubKey(byte[] encrypt_input, String certificate_path) throws Exception {
		FileInputStream fin = new FileInputStream(certificate_path);
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);
		PublicKey publicKey = certificate.getPublicKey();
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return encryptCipher.doFinal(encrypt_input);
	}

	public static Optional<String> authenticate_excellon_user(String host) throws Exception {

		byte[] encryptedMessageBytes = RsaEncryptExcellonPubKey(EXACT_PORTAL_CLIENT_SECRET,
				EXACT_PUBLIC_KEY_CERTIFICTAE_PATH);

		String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

		auth_req_body_pojo req_body = new auth_req_body_pojo(EXACT_PORTAL_CLIENT_ID, encodedMessage);
		System.out.println(new Gson().toJson(req_body).toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<auth_req_body_pojo> request = new HttpEntity<>(req_body, headers);

		ResponseEntity<String> resp_ent = restTemplate.exchange(host + EXACT_AUTH_URL_DEV, HttpMethod.POST, request,
				String.class);

		
		
		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			throw new Exception("HTTP Error Code :" + resp_ent.getStatusCodeValue());
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		JsonNode tree = objectMapper.readTree(responseText);
		Boolean isAuthenticated = tree.get("IsAuthenticated").asBoolean();

		if (isAuthenticated) {
			String AuthenticationToken = tree.get("AuthenticationToken").asText();
			String AuthenticationValidTillDateTime = tree.get("AuthenticationValidTillDateTime").asText();
			LocalDateTime dateTime = LocalDateTime.parse(AuthenticationValidTillDateTime);
			JsonNode array = tree.get("AuthorizedSubscriptions");
			JsonNode subs = array.get(0);
			String subscriptionId = subs.get("SubscriptionId").asText();

			return Optional.of(AuthenticationToken);
		} else {
			String errCode = tree.get("ErrorNumber").asText();
			String errMsg = tree.get("ErrorMessage").asText();
			System.out.println("Error with code : " + errCode + "\n Error Message : " + errMsg);
			throw new Exception("Failed to Authenticate.Please try again.");
		}
	}

	// FOR EWAYBILL =============================

	public static Optional<String> authenticate_excellon_userForEwayBill() throws Exception {

		byte[] encryptedMessageBytes = RsaEncryptExcellonPubKey(EXACT_PORTAL_CLIENT_SECRET_FOR_EWB,
				EXACT_PUBLIC_KEY_CERTIFICTAE_PATH);

		String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

		auth_req_body_pojo req_body = new auth_req_body_pojo(EXACT_PORTAL_CLIENT_ID_FOR_EWB, encodedMessage);
		System.out.println(new Gson().toJson(req_body).toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<auth_req_body_pojo> request = new HttpEntity<>(req_body, headers);

		ResponseEntity<String> resp_ent = restTemplate.exchange(HOST_FOR_EWB + EXACT_AUTH_URL_DEV_FOR_EWB,
				HttpMethod.POST, request, String.class);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			throw new Exception("HTTP Error Code :" + resp_ent.getStatusCodeValue());
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		JsonNode tree = objectMapper.readTree(responseText);
		Boolean isAuthenticated = tree.get("IsAuthenticated").asBoolean();

		if (isAuthenticated) {
			String AuthenticationToken = tree.get("AuthenticationToken").asText();
			String AuthenticationValidTillDateTime = tree.get("AuthenticationValidTillDateTime").asText();
			LocalDateTime dateTime = LocalDateTime.parse(AuthenticationValidTillDateTime);
			JsonNode array = tree.get("AuthorizedSubscriptions");
			JsonNode subs = array.get(0);
			String subscriptionId = subs.get("SubscriptionId").asText();

			return Optional.of(AuthenticationToken);
		} else {
			String errCode = tree.get("ErrorNumber").asText();
			String errMsg = tree.get("ErrorMessage").asText();
			System.out.println("Error with code : " + errCode + "\n Error Message : " + errMsg);
			throw new Exception("Failed to Authenticate.Please try again.");
		}
	}

	// ---------------------------------authenticate gst
	// user------------------------------------------//

	private static byte[] createAESKey() throws NoSuchAlgorithmException {
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

	private static String encryptAsymmentricKey(String clearText) throws Exception {
		PublicKey publicKeys = getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, publicKeys);
		byte[] encryptedText = cipher.doFinal(clearText.getBytes());
		String encryptedPassword = Base64.getEncoder().encodeToString(encryptedText);
		return encryptedPassword;
	}

	private static PublicKey getPublicKey()
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		FileInputStream in = new FileInputStream(NIC_PUBLIC_KEY_PEM_PATH);
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

	private static String encryptAsymmentricKeyForEwb(String clearText) throws Exception {
		PublicKey publicKeys = getPublicKeyForEwayBill();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, publicKeys);
		byte[] encryptedText = cipher.doFinal(clearText.getBytes());
		String encryptedPassword = Base64.getEncoder().encodeToString(encryptedText);
		return encryptedPassword;
	}

	private static PublicKey getPublicKeyForEwayBill()
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
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

	private static String decrptBySymmetricKeySEK(String appKey, String encryptedSek) {
		byte[] decoded = Base64.getDecoder().decode(appKey);

		Key aesKey = new SecretKeySpec(decoded, "AES"); // converts bytes(32 byte random generated) to key
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // encryption type = AES with padding PKCS5
			cipher.init(Cipher.DECRYPT_MODE, aesKey); // initiate decryption type with the key
			byte[] encryptedSekBytes = Base64.getDecoder().decode(encryptedSek); // decode the base64 encryptedSek to
																					// bytes
			byte[] decryptedSekBytes = cipher.doFinal(encryptedSekBytes); // decrypt the encryptedSek with the
																			// initialized cipher containing the
																			// key(Results in bytes)
			byte[] sekBytes = decryptedSekBytes;
			String decryptedSek = Base64.getEncoder().encodeToString(decryptedSekBytes); // convert the
																							// decryptedSek(bytes) to
																							// Base64 String
			return decryptedSek; // return results in base64 string
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception; " + e;
		}
	}

	// will take the username,password,gstin,accesstoken as input
	public static Map<String, Object> gen_auth_token_gst_user(String host, String excellon_auth_token, String gst_in,
			String username, String password) throws Exception {

		Map<String, Object> sekAndGstAuthMap = new HashMap<String, Object>();

		// create a secure key appKey
		String appKey = Base64.getEncoder().encodeToString(createAESKey());
		// create payload
		String payload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"appkey\":\"" + appKey
				+ "\",\"ForceRefreshAccessToken\": true}";
		System.out.println("Payload: Plain: " + payload);
		payload = Base64.getEncoder().encodeToString(payload.getBytes());
		payload = "{\"Data\":\"" + encryptAsymmentricKey(payload) + "\"}";
		System.out.println("Payload: Encrypted: " + payload);

		// Create Http request object with headers and payload to consume authentication
		// API
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("AuthenticationToken", excellon_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);
		headers.set("gstin", gst_in);

		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		ResponseEntity<String> resp_ent = restTemplate.exchange(host + EXACT_ACCESS_TOKEN_URL_DEV, HttpMethod.POST,
				request, String.class);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			throw new RuntimeException("Failed : HTTP error code : " + resp_ent.getStatusCode());
		}

		// read response
		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);
		String status = objectMapper.readTree(responseText).get("Status").asText();

		if (status.equals("0")) {
			throw new Exception("Error Occurred while validating GST User. Please contact Admin.");
		}
		if (status.equals("1")) {
			String authtoken = objectMapper.readTree(responseText).get("Data").get("AuthToken").asText();
			String sek = objectMapper.readTree(responseText).get("Data").get("Sek").asText();
			System.out.println("Authtoken: " + authtoken);
			System.out.println("Encrypted SEK: " + sek);
			sek = decrptBySymmetricKeySEK(appKey, sek);
			System.out.println("Decrypted SEK: " + sek);

			String TokenExpiry = objectMapper.readTree(responseText).get("Data").get("TokenExpiry").asText();
			System.out.println("TokenExpiry::" + TokenExpiry);

			sekAndGstAuthMap.put("DATA_FETCHED", true);
			sekAndGstAuthMap.put("EXCLN_AUTH_TOKEN", excellon_auth_token);
			sekAndGstAuthMap.put("EXCLN_GST_USER_AUTH_TOKEN", authtoken);
			sekAndGstAuthMap.put("EXCLN_SEK", sek);
			sekAndGstAuthMap.put("TOKEN_EXPIRY", TokenExpiry);
		}
		return sekAndGstAuthMap;
	}

	// FOR EWAYBILL==================
	public static Map<String, Object> gen_auth_token_gst_user_for_ewb(String excellon_auth_token, String gst_in,
			String username, String password) throws Exception {

		Map<String, Object> sekAndGstAuthMap = new HashMap<String, Object>();

		String appKey = Base64.getEncoder().encodeToString(createAESKey());

		// create pojo
		String payload = "{\"action\":\"ACCESSTOKEN\",\"username\":\"" + username + "\",\"password\":\"" + password
				+ "\",\"app_key\":\"" + appKey + "\"}";

		System.out.println("Payload: Plain: " + payload);

		payload = Base64.getEncoder().encodeToString(payload.getBytes());
		payload = "{\"Data\":\"" + encryptAsymmentricKeyForEwb(payload) + "\"}";
		System.out.println("Final PayLoad :: " + payload);

		// Create Http request object with headers and payload to consume authentication
		// API
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("AuthenticationToken", excellon_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID_FOR_EWAYBILL);
		headers.set("gstin", gst_in);

		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		// Object res = restTemplate.postForObject(EXACT_ACCESS_TOKEN_URL_DEV,
		// request,Object.class);

		ResponseEntity<String> resp_ent = restTemplate.exchange(HOST_FOR_EWB + EXACT_ACCESS_TOKEN_FOR_EWB_URL_DEV,
				HttpMethod.POST, request, String.class);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			throw new RuntimeException("Failed : HTTP error code : " + resp_ent.getStatusCode());
		}

		// read response
		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);
		String status = objectMapper.readTree(responseText).get("status").asText();

		if (status.equals("0")) {
			throw new Exception("Error Occurred while validating GST User. Please contact Admin.");
		}
		if (status.equals("1")) {
			String authtoken = objectMapper.readTree(responseText).get("authtoken").asText();
			String sek = objectMapper.readTree(responseText).get("sek").asText();
			System.out.println("Authtoken: " + authtoken);
			System.out.println("Encrypted SEK: " + sek);
			String dec_sek = decrptBySymmetricKeySEK(appKey, sek);
			System.out.println("Decrypted SEK: " + dec_sek);

			sekAndGstAuthMap.put("DATA_FETCHED", true);
			sekAndGstAuthMap.put("EXCLN_AUTH_TOKEN", excellon_auth_token);
			sekAndGstAuthMap.put("EXCLN_GST_USER_AUTH_TOKEN", authtoken);
			sekAndGstAuthMap.put("EXCLN_SEK", dec_sek);
			sekAndGstAuthMap.put("ENC_EXCLN_SEK", sek);

		}
		return sekAndGstAuthMap;
	}

	// -------------------------general encryption and decryption of
	// request/response -----------------------------//

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
			return "Exception " + e;
		}
	}

	public static String decryptBySymmentricKey(String data, String decryptedSek) {
		byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
		Key aesKey = new SecretKeySpec(sekByte, "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte[] decordedValue = Base64.getMimeDecoder().decode(data);
			byte[] decValue = cipher.doFinal(decordedValue);
			return new String(decValue);
		} catch (Exception e) {
			return "Exception " + e;
		}
	}

	// ---------------------------------------------CALLING
	// APIS----------------------------------------//
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEInvoiceResponse(EInvoiceData single_inv_tran, ExcellonGenIrnResponse genIrnResp, String comp_cd,
			String divId, String finYear, String finyrFlag) throws Exception {
		EInvoiceHeader header = null;
		EInvoiceHeader_Arc headerArc = null;
		String sum_dsp_dtl_id = single_inv_tran.getItemList().get(0).getOrdLineRef();

		if (finyrFlag.equals(MedicoConstants.CURRENT)) {
			Sum_Disp_Detail detail = dispatchrepository.sumdispatchDtlById(Long.valueOf(sum_dsp_dtl_id));
			header = einvoicerepo.getObjectByID(detail.getSumdspdtl_sumdsp_id());
			if (header == null) {
				header = new EInvoiceHeader();
				header.setTrn_id(detail.getSumdspdtl_sumdsp_id());
				header.setComp_code(comp_cd);
				header.setFin_year_id(Long.valueOf(finYear));
				header.setTrn_type(single_inv_tran.getDocDtls().getTyp());
				header.setTrn_number(single_inv_tran.getDocDtls().getNo());
				header.setTrn_date(Utility.convertStringtoDate(single_inv_tran.getDocDtls().getDt()));
				header.setTrn_result("Y");
				header.setTrn_ack_date(Utility.getDateformatYYYYDDMMHH(genIrnResp.getAckDt()));
				header.setTrn_ack_no(genIrnResp.getAckNo());
				header.setTrn_action_type("E");
				System.out.println("sig" + genIrnResp.getSignedInvoice().length());
				header.setTrn_inv_sign(genIrnResp.getSignedInvoice());
				header.setTrn_irn_number(genIrnResp.getIrn());
				header.setTrn_qr_code(genIrnResp.getSignedQRCode());
				header.setTrn_status(genIrnResp.getStatus());
				header.setTrn_ewaybillno(genIrnResp.getEwbNo());
				if (genIrnResp.getEwbNo() != null && !genIrnResp.getEwbNo().isEmpty()
						&& !genIrnResp.getEwbNo().equals("null")) {
					header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
					header.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbValidTill()));
				}
				header.setIrncancel("N");
				header.setEwaybillcancel("N");
				header.setDiv_id(Long.valueOf(divId));
				transactionalRepository.persist(header);
			} else {
				header.setTrn_id(detail.getSumdspdtl_sumdsp_id());
				header.setComp_code(comp_cd);
				header.setFin_year_id(Long.valueOf(finYear));
				header.setTrn_type(single_inv_tran.getDocDtls().getTyp());
				header.setTrn_number(single_inv_tran.getDocDtls().getNo());
				header.setTrn_result(genIrnResp.getSuccess());
				header.setTrn_ack_no(genIrnResp.getAckNo());
				header.setTrn_action_type("E");
				header.setTrn_inv_sign(genIrnResp.getSignedInvoice());
				header.setTrn_irn_number(genIrnResp.getIrn());
				header.setTrn_qr_code(genIrnResp.getSignedQRCode());
				header.setTrn_status(genIrnResp.getStatus());
				header.setTrn_ewaybillno(genIrnResp.getEwbNo());
				header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
				if (genIrnResp.getEwbNo() != null && !genIrnResp.getEwbNo().isEmpty()
						&& !genIrnResp.getEwbNo().equals("null")) {
					header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
					header.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbValidTill()));
				}
				if (genIrnResp.getStatus().equalsIgnoreCase("CNL")) {
					header.setIrncancel("Y");
				}
				transactionalRepository.update(header);
			}
		} else if (finyrFlag.equals(MedicoConstants.PREVIOUS)) {
			Sum_Disp_Detail_Arc detail = dispatchrepository.sumdispatchArcDtlBysumdspDtlId(Long.valueOf(sum_dsp_dtl_id),
					finYear);
			headerArc = einvoicerepo.getObjectByIDAndFinYear(detail.getSumdspdtl_sumdsp_id(), finYear);

			if (headerArc == null) {
				headerArc = new EInvoiceHeader_Arc();
				headerArc.setTrn_id(detail.getSumdspdtl_sumdsp_id());
				headerArc.setComp_code(comp_cd);
				headerArc.setFin_year_id(Long.valueOf(finYear));
				headerArc.setTrn_type(single_inv_tran.getDocDtls().getTyp());
				headerArc.setTrn_number(single_inv_tran.getDocDtls().getNo());
				headerArc.setTrn_date(Utility.convertStringtoDate(single_inv_tran.getDocDtls().getDt()));
				headerArc.setTrn_result(genIrnResp.getSuccess());
				headerArc.setTrn_ack_date(Utility.getDateformatYYYYDDMMHH(genIrnResp.getAckDt()));
				headerArc.setTrn_ack_no(genIrnResp.getAckNo());
				headerArc.setTrn_action_type("E");
				System.out.println("sig" + genIrnResp.getSignedInvoice().length());
				headerArc.setTrn_inv_sign(genIrnResp.getSignedInvoice());
				headerArc.setTrn_irn_number(genIrnResp.getIrn());
				headerArc.setTrn_qr_code(genIrnResp.getSignedQRCode());
				headerArc.setTrn_status(genIrnResp.getStatus());
				headerArc.setTrn_ewaybillno(genIrnResp.getEwbNo());
				if (genIrnResp.getEwbNo() != null) {
					headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
					headerArc.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbValidTill()));
				}
				headerArc.setIrncancel("N");
				headerArc.setEwaybillcancel("N");
				headerArc.setDiv_id(Long.valueOf(divId));
				headerArc.setSlno(0l);
				transactionalRepository.persist(headerArc);

				headerArc.setSlno(headerArc.getArchive_id());
				transactionalRepository.update(headerArc);
			} else {
				headerArc.setTrn_id(detail.getSumdspdtl_sumdsp_id());
				headerArc.setComp_code(comp_cd);
				headerArc.setFin_year_id(Long.valueOf(finYear));
				headerArc.setTrn_type(single_inv_tran.getDocDtls().getTyp());
				headerArc.setTrn_number(single_inv_tran.getDocDtls().getNo());
				headerArc.setTrn_result(genIrnResp.getSuccess());
				;
				headerArc.setTrn_ack_no(genIrnResp.getAckNo());
				headerArc.setTrn_action_type("E");
				headerArc.setTrn_inv_sign(genIrnResp.getSignedInvoice());
				headerArc.setTrn_irn_number(genIrnResp.getIrn());
				headerArc.setTrn_qr_code(genIrnResp.getSignedQRCode());
				headerArc.setTrn_status(genIrnResp.getStatus());
				headerArc.setTrn_ewaybillno(genIrnResp.getEwbNo());
				headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
				if (genIrnResp.getEwbNo() != null) {
					headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
					headerArc.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbValidTill()));
				}
				if (genIrnResp.getStatus().equalsIgnoreCase("CNL")) {
					headerArc.setIrncancel("Y");
				}
				transactionalRepository.update(headerArc);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEWayBillByIrnResponse(EInvoiceByIrnData single_inv_tran, ExcellonGenIrnResponse genIrnResp)
			throws Exception {
		System.out.println("********");
		System.out.println("Saving For Irn " + single_inv_tran.getIrn());
		EInvoiceHeader header = einvoicerepo.getEInvoiceHeaderByIrn(single_inv_tran.getIrn().trim());
		header.setTrn_ewaybillno(genIrnResp.getEwbNo());
		header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
		header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));

		transactionalRepository.update(header);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save_cancelIrn(String IRN) throws Exception {
		EInvoiceHeader header = null;
		header = einvoicerepo.getEInvoiceHeaderByIrn(IRN);
		header.setIrncancel("Y");
		transactionalRepository.update(header);

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save_cancelEwayBill(String ewyBillNo) throws Exception {
		EInvoiceHeader header = einvoicerepo.getEInvoiceHeaderByEWB(ewyBillNo.trim());
		header.setEwaybillcancel("Y");
		transactionalRepository.update(header);
	}

	public String getEwayBillDetailsForPrint(String url, String GstIn, String nic_auth_token,
			String gst_user_auth_token, String decryptedSEK, String ewbNo) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("gstin", GstIn);
		headers.set("authToken", gst_user_auth_token);
		headers.set("AuthenticationToken", nic_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID_FOR_EWAYBILL);
		HttpEntity<String> request = new HttpEntity<>(headers);

		// add params
		url = url.concat("?ewbNo=" + ewbNo);
		System.out.println("URL after formatting ::" + url);
		ResponseEntity<String> resp_ent = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			return "Failed : HTTP error code : " + resp_ent.getStatusCode();
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		JsonNode responseDataNode = objectMapper.readTree(responseText);
		String status = responseDataNode.get("status").asText();

		if (status.equals("0")) {
			return "Failed To Get E-WayBill Data";
		} else if (status.equals("1")) {
			System.out.println("Success");
			String encrypted_responseData = responseDataNode.get("data").asText();
			String enc_rek = responseDataNode.get("rek").asText();
			String hmac = responseDataNode.get("hmac").asText();

			String decrypted_rek = decryptBySymmentricKey(enc_rek, decryptedSEK);
			System.out.println("decrypted_rek:::" + decrypted_rek);

			String decrypted_data = decryptBySymmentricKey(encrypted_responseData, decrypted_rek);
			System.out.println("decrypted response ::::" + decrypted_data);
			// JsonNode successResponseData = objectMapper.readTree(decrypted_data);
			return null;
		}

		return null;
	}

	// check 1 : decrypt with sek not ek -- failed
	// check2 : use encrypted rek -- failed\
	// check 3 : try different way to decrypt the data

	public static String decryptBySymmentricKeyWithoutMime(String data, String decryptedSek) {
		{
			byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
			Key aesKey = new SecretKeySpec(sekByte, "AES");
			try {
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, aesKey);
				byte[] decordedValue = Base64.getDecoder().decode(data);
				byte[] decValue = cipher.doFinal(decordedValue);
				return new String(decValue);
			} catch (Exception e) {
				return "Exception " + e;
			}
		}
	}

	// stockist changes

	public String getIrnDetailsByDocDtlsOrSaveNew(String host, String url, String GstIn, String username,
			String nic_auth_token, String gst_user_auth_token, String decryptedSEK, EInvoiceData single_inv_tran,
			String comp_cd, String divId, String finYear, String finyrFlag, String gen_eway) throws Exception {
		String returnString = null;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + GET_IRN_DETAILS_BY_DOC_DTLS)
				.queryParam("doctype", "INV")
				.queryParam("docnum", single_inv_tran.getDocDtls().getNo().replaceAll("\\s+", ""))
				.queryParam("docdate", single_inv_tran.getDocDtls().getDt());

		String url_for_irn_by_doc_dtls = builder.toUriString();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Gstin", GstIn);
		headers.set("user_name", username);
		headers.set("AuthToken", gst_user_auth_token);
		headers.set("AuthenticationToken", nic_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);

		// save to json req
		String fileName = single_inv_tran.getDocDtls().getNo() + "REQ_EINDUPL" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, url_for_irn_by_doc_dtls);

		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> resp_ent = restTemplate.exchange(url_for_irn_by_doc_dtls, HttpMethod.GET, request,
				String.class);
		System.out.println("resp_ent:::" + resp_ent);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			return "Failed : HTTP error code : " + resp_ent.getStatusCode();
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		// save to json resp
		fileName = single_inv_tran.getDocDtls().getNo() + "RES_EINDUPL" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, responseText);

		JsonNode responseDataNode = objectMapper.readTree(responseText);
		String status = responseDataNode.get("Status").asText();
		// save error responses to json file also

		if (status.equals("0")) {
			System.out.println("Not a duplicate IRN . Calling the generate new function");
			JsonNode errDtlsArr = responseDataNode.get("ErrorDetails");
			System.out.println("errDtlsArr::" + errDtlsArr.toString());
			// old logic commented
//			String error_code = null;
//			boolean is_irn_not_found_ = false;
//
//			Long irn_not_found_er_cd = 2154L;
//			// "ErrorCode":"2154","ErrorMessage":"IRN details are not found"
//			if (errDtlsArr.isArray()) {
//				for (JsonNode obj : errDtlsArr) {
//					error_code = obj.get("ErrorCode").asText();
//					if (Long.valueOf(error_code).equals(irn_not_found_er_cd)) {
//						is_irn_not_found_ = true;
//						break;
//					}
//				}
//			} else {
//				error_code = errDtlsArr.get("ErrorCode").asText();
//				if (Long.valueOf(error_code).equals(irn_not_found_er_cd)) {
//					is_irn_not_found_ = true;
//				}
//			}
//			System.out.println("error_code:::" + error_code + "::: is_irn_not_found_arr:::" + is_irn_not_found_);
//			if (is_irn_not_found_) {
//				return this.generateEInvoiceExcellonStockist(url, GstIn, username, nic_auth_token, gst_user_auth_token,
//						decryptedSEK, single_inv_tran, comp_cd, divId, finYear, finyrFlag, gen_eway);
//			} else {
//				return "Error Occurred.Please contact Support.";
//			}
			StringBuilder err_response = new StringBuilder();
			JsonNode error = responseDataNode.get("ErrorDetails");
			if (error.isArray()) {
				for (JsonNode obj : error) {
					err_response.append("[" + obj.get("ErrorCode").asText() + "]");
					err_response.append(" - ");
					err_response.append(obj.get("ErrorMessage").asText());
					err_response.append(System.lineSeparator());
				}
			} else {
				err_response.append("[" + error.get("ErrorCode").asText() + "]");
				err_response.append(" - ");
				err_response.append(error.get("ErrorMessage").asText());
			}
			return err_response.toString();

		} else if (status.equals("1")) {
			System.out.println("Success");
			String encrypted_responseData = responseDataNode.get("Data").asText();
			String decrypted_data = decryptBySymmentricKey(encrypted_responseData, decryptedSEK);
			System.out.println("decrypted response ::::" + decrypted_data);
			JsonNode successResponseData = objectMapper.readTree(decrypted_data);

			// read properties
			ExcellonGenIrnResponse genIrnResp = new ExcellonGenIrnResponse();
			genIrnResp.setAckDt(successResponseData.get("AckDt").asText());
			genIrnResp.setAckNo(successResponseData.get("AckNo").asText());
			genIrnResp.setIrn(successResponseData.get("Irn").asText());
			genIrnResp.setSignedInvoice(successResponseData.get("SignedInvoice").asText());
			genIrnResp.setSignedQRCode(successResponseData.get("SignedQRCode").asText());
			genIrnResp.setStatus(successResponseData.get("Status").asText());

			if (gen_eway.trim().equals("Y")) {
				if (successResponseData.get("EwbNo") != null
						&& !successResponseData.get("EwbNo").asText().trim().equals("null")) {
					genIrnResp.setEwbDt(successResponseData.get("EwbDt").asText());
					genIrnResp.setEwbNo(successResponseData.get("EwbNo").asText());
					genIrnResp.setEwbValidTill(successResponseData.get("EwbValidTill").asText());
				} else {
					returnString = "Failed To generate EWayBill.";
				}
			}
			// call the save function here
			this.saveEInvoiceResponseStockist(single_inv_tran, genIrnResp, comp_cd, divId, finYear, finyrFlag);
			// save to json resp
			fileName = single_inv_tran.getDocDtls().getNo() + "RES_EINDUPL_DCRY" + new Date().getTime() + ".json";
			MedicoConstants.saveToJson(fileName, decrypted_data);

			return returnString;
		}

		return null;
	}

	public String generateEInvoiceExcellonStockist(String host, String url, String GstIn, String username,
			String nic_auth_token, String gst_user_auth_token, String decryptedSEK, EInvoiceData single_inv_tran,
			String comp_cd, String divId, String finYear, String finyrFlag, String gen_eway) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Gstin", GstIn);
		headers.set("user_name", username);
		headers.set("AuthToken", gst_user_auth_token);
		headers.set("AuthenticationToken", nic_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);

		// create a copy variable and then copy all data from send data fn
		Gson gson = new Gson();
		EInvoiceData send_to_nic = gson.fromJson(gson.toJson(single_inv_tran), EInvoiceData.class);
		// remove spaces from invocie no //19-04-24
		String doc_no_for_nic = send_to_nic.getDocDtls().getNo().replaceAll("\\s+", "");
		send_to_nic.getDocDtls().setNo(doc_no_for_nic);

		String payload = gson.toJson(send_to_nic).toString();
		System.out.println("send_to_nic:::" + payload);

		// save to json req
		String fileName = single_inv_tran.getDocDtls().getNo() + "REQ_EINGEN" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, payload);

		payload = "{\"data\":\"" + encryptBySymmetricKey(payload, decryptedSEK) + "\"}";

		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		ResponseEntity<String> resp_ent = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		// check status of file
		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			return "Failed : HTTP error code : " + resp_ent.getStatusCode();
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		// save to json reSP
		fileName = single_inv_tran.getDocDtls().getNo() + "RES_EINGEN" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, responseText);

		JsonNode responseDataNode = objectMapper.readTree(responseText);
		String status = responseDataNode.get("Status").asText();

		if (status.equals("0")) {
//			System.out.println("Failed");
//			//return "Failed to generate IRN";
//			return responseDataNode.get("ErrorDetails").asText();
			StringBuilder err_response = new StringBuilder();
			JsonNode error = responseDataNode.get("ErrorDetails");
			if (error.isArray()) {
				for (JsonNode obj : error) {
					err_response.append("[" + obj.get("ErrorCode").asText() + "]");
					err_response.append(" - ");
					err_response.append(obj.get("ErrorMessage").asText());
					err_response.append(System.lineSeparator());
				}
			} else {
				err_response.append("[" + error.get("ErrorCode").asText() + "]");
				err_response.append(" - ");
				err_response.append(error.get("ErrorMessage").asText());
			}
			String error_code;
			// check if error code is "ErrorCode":"2150","ErrorMessage":"Duplicate IRN"
			if (error.isArray()) {
				error_code = error.get(0).get("ErrorCode").asText().trim();
			} else {
				error_code = error.get("ErrorCode").asText().trim();
			}
			if (error_code.equalsIgnoreCase("2150")) {
				return this.getIrnDetailsByDocDtlsOrSaveNew(host, url, GstIn, username, nic_auth_token,
						gst_user_auth_token, decryptedSEK, single_inv_tran, comp_cd, divId, finYear, finyrFlag,
						gen_eway);
			} else
				return err_response.toString();
		}
		if (status.equals("1")) {
			System.out.println("Success");
			String encrypted_responseData = responseDataNode.get("Data").asText();
			String decrypted_data = decryptBySymmentricKey(encrypted_responseData, decryptedSEK);
			System.out.println("decrypted response ::::" + decrypted_data);
			JsonNode successResponseData = objectMapper.readTree(decrypted_data);

			ExcellonGenIrnResponse genIrnResp = new ExcellonGenIrnResponse();
			genIrnResp.setAckDt(successResponseData.get("AckDt").asText());
			genIrnResp.setAckNo(successResponseData.get("AckNo").asText());
			genIrnResp.setIrn(successResponseData.get("Irn").asText());
			genIrnResp.setSignedInvoice(successResponseData.get("SignedInvoice").asText());
			genIrnResp.setSignedQRCode(successResponseData.get("SignedQRCode").asText());
			genIrnResp.setStatus(successResponseData.get("Status").asText());
			String returnString = null;
			if (gen_eway.trim().equals("Y")) {
				if (successResponseData.get("EwbNo") != null
						&& !successResponseData.get("EwbNo").asText().trim().equals("null")) {
					genIrnResp.setEwbDt(successResponseData.get("EwbDt").asText());
					genIrnResp.setEwbNo(successResponseData.get("EwbNo").asText());
					genIrnResp.setEwbValidTill(successResponseData.get("EwbValidTill").asText());
				} else {
					returnString = "Failed To generate EWayBill.";
				}
			}
			 this.saveEInvoiceResponseStockist(single_inv_tran, genIrnResp, comp_cd,
			 divId, finYear, finyrFlag);

			// save to json resp
			fileName = single_inv_tran.getDocDtls().getNo() + "RES_EINGEN_DCRY" + new Date().getTime() + ".json";
			MedicoConstants.saveToJson(fileName, decrypted_data);

			return returnString;
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEInvoiceResponseStockist(EInvoiceData single_inv_tran, ExcellonGenIrnResponse genIrnResp,
			String comp_cd, String divId, String finYear, String finyrFlag) throws Exception {
		EInvoiceHeaderStockist header = null;
		EInvoiceHeader_Arc_Stockist headerArc = null;
		String dsp_dtl_id = single_inv_tran.getItemList().get(0).getOrdLineRef();

		if (finyrFlag.equals(MedicoConstants.CURRENT)) {
			Dispatch_Detail detail = dispatchrepository.dispatchDtlById(Long.valueOf(dsp_dtl_id));
			header = einvoicerepo.getObjectByIDStk(detail.getDspdtlDspId());
			if (header == null) {
				header = new EInvoiceHeaderStockist();
				header.setTrn_id(detail.getDspdtlDspId());
				header.setComp_code(comp_cd);
				header.setFin_year_id(Long.valueOf(finYear));
				header.setTrn_type(single_inv_tran.getDocDtls().getTyp());
				header.setTrn_number(single_inv_tran.getDocDtls().getNo());
				header.setTrn_date(Utility.convertStringtoDate(single_inv_tran.getDocDtls().getDt()));
				header.setTrn_result("Y");
				header.setTrn_ack_date(Utility.getDateformatYYYYDDMMHH(genIrnResp.getAckDt()));
				header.setTrn_ack_no(genIrnResp.getAckNo());
				header.setTrn_action_type("E");
				System.out.println("sig" + genIrnResp.getSignedInvoice().length());
				header.setTrn_inv_sign(genIrnResp.getSignedInvoice());
				header.setTrn_irn_number(genIrnResp.getIrn());
				header.setTrn_qr_code(genIrnResp.getSignedQRCode());
				header.setTrn_status(genIrnResp.getStatus());
				header.setTrn_ewaybillno(genIrnResp.getEwbNo());
				if (genIrnResp.getEwbNo() != null && !genIrnResp.getEwbNo().isEmpty()
						&& !genIrnResp.getEwbNo().trim().equals("null")) {
					header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
					if (genIrnResp.getEwbValidTill() != null && !genIrnResp.getEwbValidTill().isEmpty()
							&& !genIrnResp.getEwbValidTill().equals("null"))
						header.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbValidTill()));
				}
				header.setIrncancel("N");
				header.setEwaybillcancel("N");
				header.setDiv_id(Long.valueOf(divId));
				transactionalRepository.persist(header);
			} else {
				header.setTrn_id(detail.getDspdtlDspId());
				header.setComp_code(comp_cd);
				header.setFin_year_id(Long.valueOf(finYear));
				header.setTrn_type(single_inv_tran.getDocDtls().getTyp());
				header.setTrn_number(single_inv_tran.getDocDtls().getNo());
				header.setTrn_result(genIrnResp.getSuccess());
				header.setTrn_ack_no(genIrnResp.getAckNo());
				header.setTrn_action_type("E");
				header.setTrn_inv_sign(genIrnResp.getSignedInvoice());
				header.setTrn_irn_number(genIrnResp.getIrn());
				header.setTrn_qr_code(genIrnResp.getSignedQRCode());
				header.setTrn_status(genIrnResp.getStatus());
				header.setTrn_ewaybillno(genIrnResp.getEwbNo());
				header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
				if (genIrnResp.getEwbNo() != null && !genIrnResp.getEwbNo().isEmpty()
						&& !genIrnResp.getEwbNo().trim().equals("null")) {
					header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
					if (genIrnResp.getEwbValidTill() != null && !genIrnResp.getEwbValidTill().isEmpty()
							&& !genIrnResp.getEwbValidTill().equals("null"))
						header.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbValidTill()));
				}
				if (genIrnResp.getStatus().equalsIgnoreCase("CNL")) {
					header.setIrncancel("Y");
				}
				transactionalRepository.update(header);
			}
		} else if (finyrFlag.equals(MedicoConstants.PREVIOUS)) {
			Dispatch_Detail_Arc detail = dispatchrepository.dispatchArcDtlBydspDtlId(Long.valueOf(dsp_dtl_id), finYear);
			headerArc = einvoicerepo.getObjectByIDAndFinYearStk(detail.getDspdtlDspId(), finYear);

			if (headerArc == null) {
				headerArc = new EInvoiceHeader_Arc_Stockist();
				headerArc.setTrn_id(detail.getDspdtlDspId());
				headerArc.setComp_code(comp_cd);
				headerArc.setFin_year_id(Long.valueOf(finYear));
				headerArc.setTrn_type(single_inv_tran.getDocDtls().getTyp());
				headerArc.setTrn_number(single_inv_tran.getDocDtls().getNo());
				headerArc.setTrn_date(Utility.convertStringtoDate(single_inv_tran.getDocDtls().getDt()));
				headerArc.setTrn_result(genIrnResp.getSuccess());
				headerArc.setTrn_ack_date(Utility.getDateformatYYYYDDMMHH(genIrnResp.getAckDt()));
				headerArc.setTrn_ack_no(genIrnResp.getAckNo());
				headerArc.setTrn_action_type("E");
				System.out.println("sig" + genIrnResp.getSignedInvoice().length());
				headerArc.setTrn_inv_sign(genIrnResp.getSignedInvoice());
				headerArc.setTrn_irn_number(genIrnResp.getIrn());
				headerArc.setTrn_qr_code(genIrnResp.getSignedQRCode());
				headerArc.setTrn_status(genIrnResp.getStatus());
				headerArc.setTrn_ewaybillno(genIrnResp.getEwbNo());
				if (genIrnResp.getEwbNo() != null && !genIrnResp.getEwbNo().isEmpty()
						&& !genIrnResp.getEwbNo().trim().equals("null")) {
					headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
					if (genIrnResp.getEwbValidTill() != null && !genIrnResp.getEwbValidTill().isEmpty()
							&& !genIrnResp.getEwbValidTill().equals("null"))
						headerArc.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbValidTill()));
				}
				headerArc.setIrncancel("N");
				headerArc.setEwaybillcancel("N");
				headerArc.setDiv_id(Long.valueOf(divId));
				headerArc.setSlno(0l);
				transactionalRepository.persist(headerArc);

				headerArc.setSlno(headerArc.getArchive_id());
				transactionalRepository.update(headerArc);
			} else {
				headerArc.setTrn_id(detail.getDspdtlDspId());
				headerArc.setComp_code(comp_cd);
				headerArc.setFin_year_id(Long.valueOf(finYear));
				headerArc.setTrn_type(single_inv_tran.getDocDtls().getTyp());
				headerArc.setTrn_number(single_inv_tran.getDocDtls().getNo());
				headerArc.setTrn_result(genIrnResp.getSuccess());
				;
				headerArc.setTrn_ack_no(genIrnResp.getAckNo());
				headerArc.setTrn_action_type("E");
				headerArc.setTrn_inv_sign(genIrnResp.getSignedInvoice());
				headerArc.setTrn_irn_number(genIrnResp.getIrn());
				headerArc.setTrn_qr_code(genIrnResp.getSignedQRCode());
				headerArc.setTrn_status(genIrnResp.getStatus());
				headerArc.setTrn_ewaybillno(genIrnResp.getEwbNo());
				headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
				if (genIrnResp.getEwbNo() != null && !genIrnResp.getEwbNo().isEmpty()
						&& !genIrnResp.getEwbNo().trim().equals("null")) {
					headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
					if (genIrnResp.getEwbValidTill() != null && !genIrnResp.getEwbValidTill().isEmpty()
							&& !genIrnResp.getEwbValidTill().equals("null"))
						headerArc.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbValidTill()));
				}
				if (genIrnResp.getStatus().equalsIgnoreCase("CNL")) {
					headerArc.setIrncancel("Y");
				}
				transactionalRepository.update(headerArc);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEWayBillByIrnStockistResponse(EInvoiceByIrnData single_inv_tran, ExcellonGenIrnResponse genIrnResp)
			throws Exception {
		System.out.println("********");
		System.out.println("Saving For Irn " + single_inv_tran.getIrn());
		EInvoiceHeaderStockist header = einvoicerepo.getEInvoiceHeaderStockistByIrn(single_inv_tran.getIrn().trim());
		header.setTrn_ewaybillno(genIrnResp.getEwbNo());
		header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));
		header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(genIrnResp.getEwbDt()));

		transactionalRepository.update(header);
	}

	public String updateExistingOrCreateNewEWBbyIrn(String host, String url, String GstIn, String username,
			String nic_auth_token, String gst_user_auth_token, String decryptedSEK, EInvoiceByIrnData single_inv_tran)
			throws Exception {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + GET_EWB_BY_IRN).queryParam("irn",
				single_inv_tran.getIrn());

		String url_for_ewb_by_irn = builder.toUriString();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Gstin", GstIn);
		headers.set("user_name", username);
		headers.set("AuthToken", gst_user_auth_token);
		headers.set("AuthenticationToken", nic_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);

		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> resp_ent = restTemplate.exchange(url_for_ewb_by_irn, HttpMethod.GET, request,
				String.class);
		System.out.println("resp_ent:::" + resp_ent);

		// save to json req
		String fileName = single_inv_tran.getChallan_No() + "_REQ_EWBCHKDUPL" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, url_for_ewb_by_irn);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			return "Failed : HTTP error code : " + resp_ent.getStatusCode();
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		// save to json req
		fileName = single_inv_tran.getChallan_No() + "_RES_EWBCHKDUPL" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, responseText);

		JsonNode responseDataNode = objectMapper.readTree(responseText);
		String status = responseDataNode.get("Status").asText();

		if (status.equals("0")) {
			// "ErrorCode":"4005","ErrorMessage":"Eway Bill details are not found"
			Long ewb_dtls_not_found_err_cd = 4005L;
			boolean ewbDetailsNotFound = false;
			JsonNode errDtls = responseDataNode.get("ErrorDetails");
//			String errorCode = null;
//			if (errDtls.isArray()) {
//				for (JsonNode obj : errDtls) {
//					errorCode = obj.get("ErrorCode").asText();
//					if (Long.valueOf(errorCode).equals(ewb_dtls_not_found_err_cd)) {
//						ewbDetailsNotFound = true;
//						break;
//					}
//				}
//			} else {
//				errorCode = errDtls.get("ErrorCode").asText();
//				if (Long.valueOf(errorCode).equals(ewb_dtls_not_found_err_cd)) {
//					ewbDetailsNotFound = true;
//				}
//			}
//
//			System.out.println("errorCode::" + errorCode + " ewbDetailsNotFound:::" + ewbDetailsNotFound);
//			if (ewbDetailsNotFound) {
//				return this.generateEWayBillByIrnStockistExcellon(url, GstIn, username, nic_auth_token,
//						gst_user_auth_token, decryptedSEK, single_inv_tran);
//			} else {
//				return "Error Occurred.Please contact Support.";
//			}
			StringBuilder err_response = new StringBuilder();
			JsonNode error = responseDataNode.get("ErrorDetails");
			if (error.isArray()) {
				for (JsonNode obj : error) {
					err_response.append("[" + obj.get("ErrorCode").asText() + "]");
					err_response.append(" - ");
					err_response.append(obj.get("ErrorMessage").asText());
				}
			} else {
				err_response.append(error.get("ErrorCode").asText());
				err_response.append(" - ");
				err_response.append(error.get("ErrorMessage").asText());
			}
			return err_response.toString();
		} else if (status.equals("1")) {
			System.out.println("Success");
			String encrypted_responseData = responseDataNode.get("Data").asText();
			String decrypted_data = decryptBySymmentricKey(encrypted_responseData, decryptedSEK);
			System.out.println("decrypted response ::::" + decrypted_data);
			JsonNode successResponseData = objectMapper.readTree(decrypted_data);

			ExcellonGenIrnResponse genIrnResp = new ExcellonGenIrnResponse();
			genIrnResp.setEwbDt(successResponseData.get("EwbDt").asText());
			genIrnResp.setEwbNo(successResponseData.get("EwbNo").asText());
			genIrnResp.setEwbValidTill(successResponseData.get("EwbValidTill").asText());

			// save to json resp
			fileName = single_inv_tran.getChallan_No() + "_RES_EWBCHKDUPL_DCRY" + new Date().getTime() + ".json";
			MedicoConstants.saveToJson(fileName, decrypted_data);

			this.saveEWayBillByIrnStockistResponse(single_inv_tran, genIrnResp);
		}
		return null;
	}

	public String generateEWayBillByIrnStockistExcellon(String host,String url, String GstIn, String username,
			String nic_auth_token, String gst_user_auth_token, String decryptedSEK, EInvoiceByIrnData single_inv_tran)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Gstin", GstIn);
		headers.set("user_name", username);
		headers.set("AuthToken", gst_user_auth_token);
		headers.set("AuthenticationToken", nic_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);

		String payload = new Gson().toJson(single_inv_tran).toString();

		// save to json req
		String fileName = single_inv_tran.getChallan_No() + "REQ_EWBGEN" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, payload);

		System.out.println("payload:::>" + payload);

		payload = "{\"data\":\"" + encryptBySymmetricKey(payload, decryptedSEK) + "\"}";

		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		ResponseEntity<String> resp_ent = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			return "Failed : HTTP error code : " + resp_ent.getStatusCode();
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		// save to json resp
		fileName = single_inv_tran.getChallan_No() + "RES_EWBGEN" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, responseText);

		JsonNode responseDataNode = objectMapper.readTree(responseText);
		String status = responseDataNode.get("Status").asText();

		if (status.equals("0")) {
			// return "Failed To Generate E-WayBill";
			// return the errors with the codes
			StringBuilder err_response = new StringBuilder();
			JsonNode error = responseDataNode.get("ErrorDetails");
			if (error.isArray()) {
				for (JsonNode obj : error) {
					err_response.append("[" + obj.get("ErrorCode").asText() + "]");
					err_response.append(" - ");
					err_response.append(obj.get("ErrorMessage").asText());
				}
			} else {
				err_response.append(error.get("ErrorCode").asText());
				err_response.append(" - ");
				err_response.append(error.get("ErrorMessage").asText());
			}
			String error_code;
			// check if error code is "ErrorCode":"4002","ErrorMessage":"EwayBill is already generated for this IRN"
			if (error.isArray()) {
				error_code = error.get(0).get("ErrorCode").asText().trim();
			} else {
				error_code = error.get("ErrorCode").asText().trim();
			}
			if (error_code.equalsIgnoreCase("4002")) {
				return this.updateExistingOrCreateNewEWBbyIrn(host, url, GstIn, username, nic_auth_token, 
						gst_user_auth_token, decryptedSEK, single_inv_tran);
			} else
				return err_response.toString();


		} else if (status.equals("1")) {
			System.out.println("Success");
			String encrypted_responseData = responseDataNode.get("Data").asText();
			String decrypted_data = decryptBySymmentricKey(encrypted_responseData, decryptedSEK);
			System.out.println("decrypted response ::::" + decrypted_data);
			JsonNode successResponseData = objectMapper.readTree(decrypted_data);

			ExcellonGenIrnResponse genIrnResp = new ExcellonGenIrnResponse();
			genIrnResp.setEwbDt(successResponseData.get("EwbDt").asText());
			genIrnResp.setEwbNo(successResponseData.get("EwbNo").asText());
			genIrnResp.setEwbValidTill(successResponseData.get("EwbValidTill").asText());
			this.saveEWayBillByIrnStockistResponse(single_inv_tran, genIrnResp);

			// save to json resp
			fileName = single_inv_tran.getChallan_No() + "RES_EWBGEN_DCRY" + new Date().getTime() + ".json";
			MedicoConstants.saveToJson(fileName, decrypted_data);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save_cancelIrnStockist(String IRN) throws Exception {
		EInvoiceHeaderStockist header = null;
		header = einvoicerepo.getEInvoiceHeaderStockistByIrn(IRN);
		header.setIrncancel("Y");
		transactionalRepository.update(header);

	}

	public String cancelIRNStockist(String url, String GstIn, String username, String nic_auth_token,
			String gst_user_auth_token, String decryptedSEK, EInvoiceCancelData single_inv_tran) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Gstin", GstIn);
		headers.set("user_name", username);
		headers.set("AuthToken", gst_user_auth_token);
		headers.set("AuthenticationToken", nic_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);

		String payload = new Gson().toJson(single_inv_tran).toString();

		// save to json req
		String fileName = "REQ_EINCANC" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, payload);

		System.out.println("payload:::>" + payload);

		payload = "{\"data\":\"" + encryptBySymmetricKey(payload, decryptedSEK) + "\"}";

		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		ResponseEntity<String> resp_ent = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			return "Failed : HTTP error code : " + resp_ent.getStatusCode();
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		// save to json resp
		fileName = "RES_EINCANC" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, responseText);

		JsonNode responseDataNode = objectMapper.readTree(responseText);
		String status = responseDataNode.get("Status").asText();
		if (status.equals("0")) {
			return "Failed To Cancel E-Invoice";
		} else if (status.equals("1")) {
			System.out.println("Success");
			String encrypted_responseData = responseDataNode.get("Data").asText();
			String decrypted_data = decryptBySymmentricKey(encrypted_responseData, decryptedSEK);
			System.out.println("decrypted response ::::" + decrypted_data);
			JsonNode successResponseData = objectMapper.readTree(decrypted_data);

			// save cancellation of einvoice
			this.save_cancelIrnStockist(successResponseData.get("Irn").asText());

			// save to json resp success
			fileName = "RES_EINCANC_DCRY" + new Date().getTime() + ".json";
			MedicoConstants.saveToJson(fileName, decrypted_data);

			return "Cancellation Success !!";
		}
		return null;

	}

	public String cancelEWBStockist(String url, String GstIn, String username, String nic_auth_token,
			String gst_user_auth_token, String decryptedSEK, String ewbNo) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Gstin", GstIn);
		headers.set("user_name", username);
		headers.set("AuthToken", gst_user_auth_token);
		headers.set("AuthenticationToken", nic_auth_token);
		headers.set("ExactSubscriptionId", EXACT_SUBSCRIPTION_ID);

		String payload = "{\"ewbNo\":" + ewbNo + ",\"cancelRsnCode\": 2,\"cancelRmrk\": \"Cancelled the order\"}";

		// save to json req
		String fileName = ewbNo + "_REQ_EWBCANC" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, payload);

		System.out.println("payload:::>" + payload);

		payload = "{\"action\": \"CANEWB\",\"data\":\"" + encryptBySymmetricKey(payload, decryptedSEK) + "\"}";

		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		ResponseEntity<String> resp_ent = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		if (!resp_ent.getStatusCode().equals(HttpStatus.OK)) {
			return "Failed : HTTP error code : " + resp_ent.getStatusCode();
		}

		String responseText = resp_ent.getBody();
		System.out.println("Response:" + responseText);

		// save to json resp
		fileName = ewbNo + "_RES_EWBCANC" + new Date().getTime() + ".json";
		MedicoConstants.saveToJson(fileName, responseText);

		JsonNode responseDataNode = objectMapper.readTree(responseText);
		String status = responseDataNode.get("status").asText();

		if (status.equals("0")) {

			return "Failed To Cancel E-WayBill";
		} else if (status.equals("1")) {
			System.out.println("Success");
			String encrypted_responseData = responseDataNode.get("data").asText();
			String decrypted_data = decryptBySymmentricKey(encrypted_responseData, decryptedSEK);
			System.out.println("decrypted response ::::" + decrypted_data);
			JsonNode successResponseData = objectMapper.readTree(decrypted_data);

			// save cancellation of einvoice
			this.save_cancelEWBStockist(successResponseData.get("ewayBillNo").asText());

			// save to json resp success
			fileName = ewbNo + "_RES_EWBCANC_DCRY" + new Date().getTime() + ".json";
			MedicoConstants.saveToJson(fileName, decrypted_data);

			return "Cancellation Success !!";
		}
		return null;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save_cancelEWBStockist(String ewyBillNo) throws Exception {
		EInvoiceHeaderStockist header = einvoicerepo.getEInvoiceHeaderStockistByEWB(ewyBillNo.trim());
		header.setEwaybillcancel("Y");
		transactionalRepository.update(header);
	}

}
