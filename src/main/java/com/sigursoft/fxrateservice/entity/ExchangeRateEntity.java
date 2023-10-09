package com.sigursoft.fxrateservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Table("fx_rate")
public record ExchangeRateEntity(
		@Id @Column("id") Long id,
		@Column("buy_currency") String buyCurrency,
		@Column("sell_currency") String sellCurrency,
		@Column("rate") BigDecimal rate,
		@Column("created_at") Instant createdAt
){
	public static final class Builder {
		String buyCurrency;
		String sellCurrency;
		BigDecimal rate;

		public Builder buyCurrency(String buyCurrency) {
			this.buyCurrency = buyCurrency;
			return this;
		}

		public Builder sellCurrency(String sellCurrency) {
			this.sellCurrency = sellCurrency;
			return this;
		}

		public Builder rate(BigDecimal rate) {
			this.rate = rate;
			return this;
		}

		public ExchangeRateEntity build() {
			return new ExchangeRateEntity(null, buyCurrency, sellCurrency, rate, null);
		}
	}
}
