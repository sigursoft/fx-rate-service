package com.sigursoft.fxrateservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Table("fx_rate")
public record ExchangeRateEntity(
		@Id @Column("id") Integer id,
		@Column("buy_currency") String buyCurrency,
		@Column("sell_currency") String sellCurrency,
		@Column("rate") BigDecimal rate,
		@Column("created_at") Instant createdAt
){}
