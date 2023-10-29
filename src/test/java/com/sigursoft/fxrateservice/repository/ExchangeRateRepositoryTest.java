package com.sigursoft.fxrateservice.repository;

import com.sigursoft.fxrateservice.entity.ExchangeRateEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.math.BigDecimal;

@DataR2dbcTest
@Testcontainers
class ExchangeRateRepositoryTest {

	@SuppressWarnings("resource")
	@Container
	@ServiceConnection
	static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:10.11.5")
			.withDatabaseName("fx")
			.withUsername("user")
			.withPassword("password")
			.withCopyFileToContainer(
					MountableFile.forClasspathResource("db/schema.sql"),
					"/docker-entrypoint-initdb.d/schema.sql"
			);

	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	@Test
	void findFirstByBuyCurrencyAndSellCurrencyOrderByCreatedAtDesc() {
		// given
		var exchangeRateEntity = new ExchangeRateEntity.Builder()
				.buyCurrency("USD")
				.sellCurrency("EUR")
				.rate(new BigDecimal("0.9341"))
				.build();
		var persistedExchangeRateEntity = exchangeRateRepository.save(exchangeRateEntity).block();
		assert persistedExchangeRateEntity != null;
		var persistedExchangeRateEntityWithTimestamp = exchangeRateRepository.findById(persistedExchangeRateEntity.id()).block();
		// when
		ExchangeRateEntity result = exchangeRateRepository.findFirstByBuyCurrencyAndSellCurrencyOrderByCreatedAtDesc("USD", "EUR").block();
		// then
		assert result != null;
		assert persistedExchangeRateEntityWithTimestamp != null;
		Assertions.assertEquals(persistedExchangeRateEntityWithTimestamp, result);
	}
}