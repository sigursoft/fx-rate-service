package com.sigursoft.fxrateservice.domain;

import com.sigursoft.fxrateservice.entity.ExchangeRateEntity;

import java.math.BigDecimal;
import java.time.Instant;

public record ExchangeRate(String buyCurrency, String sellCurrency, BigDecimal rate, Instant createdAt) {
	public static ExchangeRate fromExchangeRateEntity(ExchangeRateEntity exchangeRateEntity) {
		return new Builder()
				.buyCurrency(exchangeRateEntity.buyCurrency())
				.sellCurrency(exchangeRateEntity.sellCurrency())
				.rate(exchangeRateEntity.rate())
				.createdAt(exchangeRateEntity.createdAt())
				.build();
	}

	public static final class Builder {
		String buyCurrency;
		String sellCurrency;
		BigDecimal rate;

		Instant createdAt;

		public ExchangeRate.Builder buyCurrency(String buyCurrency) {
			this.buyCurrency = buyCurrency;
			return this;
		}

		public ExchangeRate.Builder sellCurrency(String sellCurrency) {
			this.sellCurrency = sellCurrency;
			return this;
		}

		public ExchangeRate.Builder rate(BigDecimal rate) {
			this.rate = rate;
			return this;
		}

		public ExchangeRate.Builder createdAt(Instant createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public ExchangeRate build() {
			return new ExchangeRate(buyCurrency, sellCurrency, rate, createdAt);
		}
	}
}
