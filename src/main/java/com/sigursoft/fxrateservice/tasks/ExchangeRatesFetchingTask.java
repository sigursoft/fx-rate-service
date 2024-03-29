package com.sigursoft.fxrateservice.tasks;

import com.sigursoft.fxrateservice.domain.ExchangeRate;
import com.sigursoft.fxrateservice.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@ConditionalOnProperty(value = "fx.tasks.enabled", matchIfMissing = true, havingValue = "true")
public class ExchangeRatesFetchingTask {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeRatesFetchingTask.class);

	private static final String PLN = "PLN";

	@Autowired
	private WebClient polygonGatewayClient;

	@Autowired
	private ExchangeRateService exchangeRateService;

	@Value("${fx.currencies}")
	private List<String> currencies;

	@Scheduled(cron = "0 * * * * *")
	public void fetchExchangeRates() {
		var allCombinations = generateAllFxCombinations();
		try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
			allCombinations.forEach(currencyPair -> executorService.submit(() -> fetchExchangeRate(currencyPair[0], currencyPair[1])));
		}
	}

	private ArrayList<String[]> generateAllFxCombinations() {
		var pnlOnTheSellSide = currencies.stream().map(currency -> new String[] {currency, PLN}).toList();
		var pnlOnTheBuySide = currencies.stream().map(currency -> new String[] {PLN, currency}).toList();
		var allCombinations = new ArrayList<>(pnlOnTheSellSide);
		allCombinations.addAll(pnlOnTheBuySide);
		return allCombinations;
	}

	private void fetchExchangeRate(String buyCurrency, String sellCurrency) {
		logger.info("Fetching rate for " + buyCurrency + ":" + sellCurrency);
		polygonGatewayClient.get().uri("/exchange-rate/" + buyCurrency +"/" + sellCurrency).retrieve()
				.bodyToMono(ExchangeRate.class).subscribe(exchangeRateService::save);
	}
}
