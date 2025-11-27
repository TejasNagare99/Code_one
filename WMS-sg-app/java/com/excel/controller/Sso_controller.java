package com.excel.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/rest/sso")
public class Sso_controller {
	RestTemplate restTemplate = new RestTemplate();
	private static ObjectMapper objectMapper = new ObjectMapper();

	private final String url = "https://devfederate.pfizer.com/as/token.oauth2";
	private final String authCode = "fyONZM2F-KDOYGBVRQ6N0L-JYIVje_UKKUwAAAAC";
	private final String redirectUri = "https://www.sampro-pfizerindia.com:8100/stk-cfa/";
	private final String clientId = "Medico";
	private final String clientSecret = "Kgsfvdbnjmhytr*1";

	public JsonNode getAccessToken(String code, String state) {
		JsonNode responseAccessToken = null;
		try {
			// Prepare headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			// Prepare request parameters
			String requestBody = String.format(
					"grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s", authCode,
					redirectUri, clientId, clientSecret);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			// Make the POST request
			ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
			// Output the response
			System.out.println("Response Code: " + response.getStatusCodeValue());
			System.out.println("Response Body: " + response.getBody());

			responseAccessToken = objectMapper.readTree(response.getBody());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseAccessToken;
	}

	@GetMapping("/pfz_cfastk")
	public ResponseEntity<String> getAccesTokenForPfizer(@RequestParam String state, @RequestParam String code)
			throws IOException {
		// Prepare headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Prepare request parameters
		String requestBody = String.format(
				"grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s", authCode,
				redirectUri, clientId, clientSecret);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		// Make the POST request
		ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

		// Output the response
		System.out.println("Response Code: " + response.getStatusCodeValue());
		System.out.println("Response Body: " + response.getBody());

		JsonNode responseAccessToken = objectMapper.readTree(response.getBody());
//		{
//	    "access_token": "0001LEAh4XUNUoWM5MzWudrxbpcI",
//	    "refresh_token": "9AJj4EslDC0f0qDDncPc60Rz45ozgYDALLiRsMkbQdU",
//	    "token_type": "Bearer",
//	    "expires_in": 1800
//	}

		// above is th ebody that will be received
		// parse the body and make a get request for the protected resource i.e /user
		// endpoint
		// if (response.getStatusCodeValue() == 200) {
		// make the second request using the access token
		String access_tk = responseAccessToken.get("access_token").asText();
		// print the out put
		// Prepare headers
		headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + access_tk);
		requestEntity = new HttpEntity<>(null, headers);
		response = restTemplate.getForEntity("https://devfederate.pfizer.com/user", String.class);

		System.out.println("response::::" + response);
		// }

		return null;
	}

	public JsonNode getProtectedResource(String access_tk) {
		JsonNode prot_res_json = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + access_tk);
			URI uri = URI.create("https://devfederate.pfizer.com/user");
			RequestEntity<String> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
			ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
			System.out.println("Response Code: " + response.getStatusCodeValue());
			System.out.println("Response Body: " + response.getBody());
			prot_res_json = objectMapper.readTree(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  prot_res_json;
	}

}
