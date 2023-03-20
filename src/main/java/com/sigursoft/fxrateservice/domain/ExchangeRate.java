package com.sigursoft.fxrateservice.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record ExchangeRate(String buyCurrency, String sellCurrency, BigDecimal rate, Instant createdAt) {
}
