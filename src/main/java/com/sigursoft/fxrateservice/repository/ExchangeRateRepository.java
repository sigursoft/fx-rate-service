package com.sigursoft.fxrateservice.repository;

import com.sigursoft.fxrateservice.entity.ExchangeRateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ExchangeRateRepository extends ReactiveCrudRepository<ExchangeRateEntity, Long> {

	Mono<ExchangeRateEntity> findFirstByBuyCurrencyAndSellCurrencyOrderByCreatedAtDesc(String buyCurrency, String sellCurrency);
}
