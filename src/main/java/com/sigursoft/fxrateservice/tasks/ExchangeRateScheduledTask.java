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
public abstract class ExchangeRateScheduledTask {
	private static final Logger logger = LoggerFactory.getLogger(ExchangeRateScheduledTask.class);

	@Autowired
	private WebClient polygonGatewayClient;

	@Autowired
	private ExchangeRateService exchangeRateService;

	protected abstract String currency();

	@Scheduled(cron = "0 * * * * *")
	public void fetchExchangeRate() {
		logger.info("Fetching rate for " + currency());
		polygonGatewayClient.get().uri("/exchange-rate/" + currency() +"/PLN").retrieve()
				.bodyToMono(ExchangeRate.class).subscribe(exchangeRateService::save);
	}
}
