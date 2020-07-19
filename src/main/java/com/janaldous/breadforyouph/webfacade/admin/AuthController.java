package com.janaldous.breadforyouph.webfacade.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

@RestController
public class AuthController {

	@Value("${security.oauth2.resource.id}")
	private String resourceId;

	@Value("${auth0.domain}")
	private String domain;

	@Value("${auth0.clientId}")
	private String clientId;

	@RequestMapping(value = "/config", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getAppConfigs() {
		return new JSONObject().put("domain", domain).put("clientID", clientId).put("audience", resourceId).toString();
	}
}
