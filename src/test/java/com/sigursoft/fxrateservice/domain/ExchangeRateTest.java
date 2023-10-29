package com.sigursoft.fxrateservice.domain;

import com.sigursoft.fxrateservice.entity.ExchangeRateEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateTest {

	@Test
	void toExchangeRate() {
		// given
		ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9341"))
				.build();
		// when
		ExchangeRate exchangeRate = ExchangeRate.fromExchangeRateEntity(exchangeRateEntity);
		// then
		assertEquals(exchangeRate.buyCurrency(), exchangeRateEntity.buyCurrency());
		assertEquals(exchangeRate.sellCurrency(), exchangeRateEntity.sellCurrency());
		assertEquals(exchangeRate.rate(), exchangeRateEntity.rate());
		assertEquals(exchangeRate.createdAt(), exchangeRateEntity.createdAt());
	}
}