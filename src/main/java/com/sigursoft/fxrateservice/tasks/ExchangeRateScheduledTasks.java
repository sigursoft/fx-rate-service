package com.sigursoft.fxrateservice.tasks;

import com.sigursoft.fxrateservice.domain.ExchangeRate;
import com.sigursoft.fxrateservice.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExchangeRateScheduledTasks {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WebClient polygonGatewayClient;

	@Autowired
	private ExchangeRateService exchangeRateService;

	@Scheduled(cron = "0 * * * * *")
	public void fetchDollarExchangeRates() {
		logger.info("Fetching exchange rates: USD/PLN");
		polygonGatewayClient.get().uri("/exchange-rate/USD/PLN").retrieve()
				.bodyToMono(ExchangeRate.class).flatMap(usdPlnExchangeRate -> exchangeRateService.save(usdPlnExchangeRate)).block();
	}

	@Scheduled(cron = "0 * * * * *")
	public void fetchEuroExchangeRate() {
		logger.info("Fetching exchange rates: EUR/PLN");
		polygonGatewayClient.get().uri("/exchange-rate/EUR/PLN").retrieve()
				.bodyToMono(ExchangeRate.class).flatMap(eurPlnExchangeRate -> exchangeRateService.save(eurPlnExchangeRate)).block();
	}

}
