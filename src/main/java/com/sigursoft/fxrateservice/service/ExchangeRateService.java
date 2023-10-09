package com.sigursoft.fxrateservice.service;

import com.sigursoft.fxrateservice.domain.ExchangeRate;
import com.sigursoft.fxrateservice.entity.ExchangeRateEntity;
import com.sigursoft.fxrateservice.repository.ExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	public void save(ExchangeRate exchangeRate) {
		exchangeRateRepository.save(
				new ExchangeRateEntity.Builder()
						.buyCurrency(exchangeRate.buyCurrency())
						.sellCurrency(exchangeRate.sellCurrency())
						.rate(exchangeRate.rate())
						.build()
		).doOnSuccess(e -> logger.info("Saved exchange rate " + e.toString())).subscribe();
	}

	public Flux<ExchangeRateEntity> provideExchangeRates() {
		return exchangeRateRepository.findAll();
	}

	public Mono<ExchangeRateEntity> provideLatestExchangeRate(String buyCurrency, String sellCurrency) {
		return exchangeRateRepository.findFirstByBuyCurrencyAndSellCurrencyOrderByCreatedAtDesc(buyCurrency, sellCurrency);
	}
}
