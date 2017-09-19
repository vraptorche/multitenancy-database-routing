package co.jware.multitenancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MultiTenancyDatabaseRoutingApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultiTenancyDatabaseRoutingApplication.class, args);
	}
}
