package kr.hhplus.be.server;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestContainersConfiguration {

	public static final MySQLContainer<?> MYSQL_CONTAINER;
	public static final ConfluentKafkaContainer KAFKA_CONTAINER = new ConfluentKafkaContainer(
			DockerImageName.parse("confluentinc/cp-kafka:latest")
					.asCompatibleSubstituteFor("apache/kafka")
	);

	static {
		MYSQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
				.withDatabaseName("hhplus")
				.withUsername("test")
				.withPassword("test");
		MYSQL_CONTAINER.start();

		System.setProperty("spring.datasource.url", MYSQL_CONTAINER.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=UTC");
		System.setProperty("spring.datasource.username", MYSQL_CONTAINER.getUsername());
		System.setProperty("spring.datasource.password", MYSQL_CONTAINER.getPassword());

		//kafka
		KAFKA_CONTAINER
				.withEnv("KAFKA_LISTENERS", "PLAINTEXT://:9092,BROKER://:9093,CONTROLLER://:9094")
				.start();
		KAFKA_CONTAINER.start();
		System.setProperty("spring.kafka.bootstrap-servers", KAFKA_CONTAINER.getBootstrapServers());
	}

	@PreDestroy
	public void preDestroy() {
		if (MYSQL_CONTAINER.isRunning()) {
			MYSQL_CONTAINER.stop();
		}

		if (KAFKA_CONTAINER.isRunning()) {
			KAFKA_CONTAINER.stop();
		}
	}

}