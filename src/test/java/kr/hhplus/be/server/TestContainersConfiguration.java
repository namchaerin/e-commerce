package kr.hhplus.be.server;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainersConfiguration {

	// Testcontainers MySQL 컨테이너 설정
	@Container
	public static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
			.withDatabaseName("hhplus")
			.withUsername("test")
			.withPassword("test");

	// Spring 환경 속성에 동적으로 DB 연결 정보 주입
	@DynamicPropertySource
	public static void dynamicProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
		registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
	}

}