package co.jware.multitenancy;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import co.jware.multitenancy.util.RoutingDataSource;


@Configuration
@EnableConfigurationProperties
@EnableJpaRepositories("co.jware.multitenancy.repository")
@EntityScan(basePackages = "co.jware.multitenancy.domain")
public class MultitenancyDatabaseRoutingConfig {
    @Bean
    @Primary
    public DataSource dataSource(Map<Object, Object> dataSources) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(oneDataSource());
        return routingDataSource;
    }

    @Bean
    public Map dataSources() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("ONE", oneDataSource());
        map.put("TWO", twoDataSource());
        return map;
    }

    @Bean
    public DataSource oneDataSource() {
        return oneDatabaseProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public DataSource twoDataSource() {
        return twoDatabaseProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "multitenancy.datasources.one")
    public DataSourceProperties oneDatabaseProperties() {
        return new DataSourceProperties() {
        };
    }

    @Bean
    @ConfigurationProperties(prefix = "multitenancy.datasources.two")
    public DataSourceProperties twoDatabaseProperties() {
        return new DataSourceProperties() {
        };
    }
}
