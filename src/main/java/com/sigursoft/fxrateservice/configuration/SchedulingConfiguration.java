package com.sigursoft.fxrateservice.configuration;

import com.sigursoft.fxrateservice.tasks.ExchangeRateScheduledTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {

	@Value("${fx.currencies}")
	private List<String> currencies;

	@Bean
	@ConditionalOnProperty(value = "fx.tasks.enabled", matchIfMissing = true, havingValue = "true")
	public ExchangeRateScheduledTask updateRatesScheduledTask() {
		return new ExchangeRateScheduledTask() {

			@Override
			protected List<String> currencies() {
				return currencies;
			}
		};
	}
}
