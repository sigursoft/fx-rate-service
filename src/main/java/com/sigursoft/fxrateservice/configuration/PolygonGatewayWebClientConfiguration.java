package com.sigursoft.fxrateservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PolygonGatewayWebClientConfiguration {

	private final String polygonGatewayBaseUrl;

	public PolygonGatewayWebClientConfiguration(@Value("${polygon-gateway.baseUrl}") String polygonGatewayBaseUrl) {
		this.polygonGatewayBaseUrl = polygonGatewayBaseUrl;
	}

	@Bean
	public WebClient polygonGatewayClient() {
		return WebClient.builder().baseUrl(polygonGatewayBaseUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
}
