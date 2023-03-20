package com.sigursoft.fxrateservice.service;

import com.sigursoft.fxrateservice.domain.ExchangeRate;
import com.sigursoft.fxrateservice.entity.ExchangeRateEntity;
import com.sigursoft.fxrateservice.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	public Mono<ExchangeRateEntity> save(ExchangeRate exchangeRate) {
		var entity = new ExchangeRateEntity(null, exchangeRate.buyCurrency(), exchangeRate.sellCurrency(), exchangeRate.rate(), null);
		return exchangeRateRepository.save(entity);
	}

	public Flux<ExchangeRateEntity> provideExchangeRates() {
		return exchangeRateRepository.findAll();
	}

	public Mono<ExchangeRateEntity> provideLatestExchangeRate(String buyCurrency, String sellCurrency) {
		return exchangeRateRepository.findFirstByBuyCurrencyAndSellCurrencyOrderByCreatedAtDesc(buyCurrency, sellCurrency);
	}
}
