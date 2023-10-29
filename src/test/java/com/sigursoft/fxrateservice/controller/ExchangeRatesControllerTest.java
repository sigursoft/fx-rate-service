package com.sigursoft.fxrateservice.controller;

import com.sigursoft.fxrateservice.domain.ExchangeRate;
import com.sigursoft.fxrateservice.entity.ExchangeRateEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.sigursoft.fxrateservice.service.ExchangeRateService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(ExchangeRatesController.class)
class ExchangeRatesControllerTest {


	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	ExchangeRateService exchangeRateService;

	@Test
	@DisplayName("should return 200 OK with mono of latest exchange rate")
	public void shouldReturnLatestExchangeRate() {
		// arrange
		var exchangeRateEntity = new ExchangeRateEntity.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9232"))
				.build();
		var expectedResponse = new ExchangeRate.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9232"))
				.build();
		when(exchangeRateService.provideLatestExchangeRate("USD", "EUR")).thenReturn(
				Mono.just(exchangeRateEntity)
		);
		// act
		webTestClient.get()
				.uri("/exchange-rate/USD/EUR/latest")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ExchangeRate.class)
				.isEqualTo(expectedResponse);
		// assert
		verify(exchangeRateService).provideLatestExchangeRate("USD", "EUR");
	}

	@Test
	@DisplayName("should return 200 OK with flux of all exchange rate")
	public void shouldReturnAllExchangeRates() {
		// arrange
		var exchangeRateEntity = new ExchangeRateEntity.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9232"))
				.build();
		var expectedResponse = new ExchangeRate.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9232"))
				.build();
		when(exchangeRateService.provideExchangeRates()).thenReturn(
				Flux.just(exchangeRateEntity)
		);
		// act
		webTestClient.get()
				.uri("/exchange-rates")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(ExchangeRate.class)
				.isEqualTo(List.of(expectedResponse));
		// assert
		verify(exchangeRateService).provideExchangeRates();
	}
}