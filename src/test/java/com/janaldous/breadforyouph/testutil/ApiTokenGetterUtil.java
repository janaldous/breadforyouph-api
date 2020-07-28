package com.janaldous.breadforyouph.testutil;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiTokenGetterUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiTokenGetterUtil.class);
	
	@Value("${auth0.audience}")
	private String audience;
	
	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuer;
	
	@Value("${auth0.clientId}")
	private String clientId;
	
	@Value("${auth0.clientSecret}")
	private String clientSecret;
	
	public String getManagementApiToken() throws JSONException {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	 
	    JSONObject requestBody = new JSONObject();
	    requestBody.put("client_id", clientId);
	    requestBody.put("client_secret", clientSecret);
	    requestBody.put("audience", audience);
	    requestBody.put("grant_type", "client_credentials"); 
	 
	    HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);
	 
	    RestTemplate restTemplate = new RestTemplate();
	    @SuppressWarnings("unchecked")
		HashMap<String, String> result = restTemplate
	      .postForObject(issuer + "/oauth/token", request, HashMap.class);
	 
	    String accessToken = result.get("access_token"); 
	    
	    logger.info("access_token = " + accessToken);
	    
	    return accessToken;
	}
	
}
