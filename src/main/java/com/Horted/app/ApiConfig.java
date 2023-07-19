package com.Horted.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class ApiConfig {
	//get the values from the configuration file
	@Value("${paypal.client.id}")
	private String clientId;
	@Value("${paypal.client.secret}")
	private String clientSecret;
	@Value("${paypal.mode}")
	private String mode;

	@Bean
	public Map<String, String> ApiConfig() {
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		return configMap;
	}

	@Bean //this creates an authentication token so that Paypal can be acessed through my client id and secret code
	public OAuthTokenCredential oAuthTokenCredential() {

		return new OAuthTokenCredential(clientId, clientSecret, ApiConfig());
	}

	@Bean //This configues the token with the api 
	public APIContext apiContext() throws PayPalRESTException {

		APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());

		context.setConfigurationMap(ApiConfig());
		return context;
	}
}
