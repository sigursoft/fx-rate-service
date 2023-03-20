package com.sigursoft.fxrateservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Table("fx_rate")
public record ExchangeRateEntity(@Id @Column("id") Integer id, @Column("buy_currency") String buyCurrency, @Column("sell_currency") String sellCurrency, @Column("rate") BigDecimal rate, @Column("created_at") Instant createdAt) {

	@Override
	public String toString() {
		return "ExchangeRateEntity{" +
				"id=" + id +
				", buyCurrency='" + buyCurrency + '\'' +
				", sellCurrency='" + sellCurrency + '\'' +
				", rate=" + rate +
				", createdAt=" + createdAt +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ExchangeRateEntity that = (ExchangeRateEntity) o;
		return Objects.equals(id, that.id) && Objects.equals(buyCurrency, that.buyCurrency) && Objects.equals(sellCurrency, that.sellCurrency) && Objects.equals(rate, that.rate) && Objects.equals(createdAt, that.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, buyCurrency, sellCurrency, rate, createdAt);
	}
}
