package com.sigursoft.fxrateservice.controller;

import com.sigursoft.fxrateservice.domain.ExchangeRate;
import com.sigursoft.fxrateservice.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ExchangeRatesController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeRateService exchangeRateService;

	@GetMapping("/exchange-rates")
	Flux<ExchangeRate> provideExchangeRates() {
		logger.info("Providing exchange rates");
		return exchangeRateService.provideExchangeRates().map(exchangeRateEntity ->
				new ExchangeRate(exchangeRateEntity.buyCurrency(), exchangeRateEntity.sellCurrency(), exchangeRateEntity.rate(), exchangeRateEntity.createdAt())
		);
	}

	@GetMapping("/exchange-rate/{buyCurrency}/{sellCurrency}/latest")
	Mono<ExchangeRate> provideLatestExchangeRate(@PathVariable("buyCurrency") String buyCurrency, @PathVariable("sellCurrency") String sellCurrency) {
		logger.info("Providing latest exchange rate for {} and {}", buyCurrency, sellCurrency);
		return exchangeRateService.provideLatestExchangeRate(buyCurrency, sellCurrency).map(exchangeRateEntity ->
				new ExchangeRate(exchangeRateEntity.buyCurrency(), exchangeRateEntity.sellCurrency(), exchangeRateEntity.rate(), exchangeRateEntity.createdAt())
		);
	}
}
