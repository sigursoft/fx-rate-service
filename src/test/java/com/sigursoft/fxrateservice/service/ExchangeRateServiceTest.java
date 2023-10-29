package com.sigursoft.fxrateservice.service;

import com.sigursoft.fxrateservice.domain.ExchangeRate;
import com.sigursoft.fxrateservice.entity.ExchangeRateEntity;
import com.sigursoft.fxrateservice.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

	@Mock
	ExchangeRateRepository exchangeRateRepository;

	@InjectMocks
	ExchangeRateService exchangeRateService;

	@Test
	void save() {
		// given
		var exchangeRate = new ExchangeRate.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9843"))
				.build();
		var expectedExchangeRateEntity = new ExchangeRateEntity.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9843"))
				.build();
		// when
		Mockito.when(exchangeRateRepository.save(any(ExchangeRateEntity.class))).thenReturn(Mono.just(expectedExchangeRateEntity));
		exchangeRateService.save(exchangeRate);
		// then
		Mockito.verify(exchangeRateRepository, Mockito.times(1)).save(expectedExchangeRateEntity);
	}

	@Test
	void provideExchangeRates() {
		// given
		Flux<ExchangeRateEntity> response = Flux.just(
			new ExchangeRateEntity.Builder()
					.buyCurrency("USD")
					.sellCurrency("EUR")
					.rate(new BigDecimal("0.9844"))
					.build(),
			new ExchangeRateEntity.Builder()
					.buyCurrency("USD")
					.sellCurrency("EUR")
					.rate(new BigDecimal("0.9845"))
					.build()
		);
		Mockito.when(exchangeRateRepository.findAll()).thenReturn(response);
		// when
		exchangeRateService.provideExchangeRates();
		// then
		Mockito.verify(exchangeRateRepository, Mockito.times(1)).findAll();
	}

	@Test
	void provideLatestExchangeRate() {
		// given
		var buyCurrency = "USD";
		var sellCurrency = "EUR";
		var response = new ExchangeRateEntity.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9845"))
				.build();
		Mockito.when(exchangeRateRepository.findFirstByBuyCurrencyAndSellCurrencyOrderByCreatedAtDesc(buyCurrency, sellCurrency)).thenReturn(Mono.just(response));
		// when
		exchangeRateService.provideLatestExchangeRate(buyCurrency, sellCurrency);
		// then
		Mockito.verify(exchangeRateRepository, Mockito.times(1)).findFirstByBuyCurrencyAndSellCurrencyOrderByCreatedAtDesc(buyCurrency, sellCurrency);
	}
}