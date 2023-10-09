package com.sigursoft.fxrateservice.configuration;

import com.sigursoft.fxrateservice.tasks.ExchangeRateScheduledTask;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {

	@Bean
	@ConditionalOnProperty(value = "tasks.enabled", matchIfMissing = true, havingValue = "true")
	public ExchangeRateScheduledTask usdRateScheduledTask() {
		return new ExchangeRateScheduledTask() {

			@Override
			protected String currency() {
				return "USD";
			}
		};
	}

	@Bean
	@ConditionalOnProperty(value = "tasks.enabled", matchIfMissing = true, havingValue = "true")
	public ExchangeRateScheduledTask euroRateScheduledTask() {
		return new ExchangeRateScheduledTask() {

			@Override
			protected String currency() {
				return "EUR";
			}
		};
	}
}
