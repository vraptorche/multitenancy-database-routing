package co.jware.multitenancy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import co.jware.multitenancy.repository.UserRepository;
import co.jware.multitenancy.service.UserService;
import co.jware.multitenancy.service.impl.UserServiceImpl;
import co.jware.multitenancy.util.DatebaseRoutingAspect;
import co.jware.multitenancy.util.RoutingDataSource;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

import static java.util.stream.Collectors.toSet;


@Configuration
@EnableConfigurationProperties
@EnableJpaRepositories("co.jware.multitenancy.repository")
@EntityScan(basePackages = "co.jware.multitenancy.domain")
//@EnableAspectJAutoProxy
public class MultiTenancyDatabaseRoutingConfig {
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
    @ConfigurationProperties(prefix = "multitenancy.datasources.one")
    public DataSource oneDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multitenancy.datasources.two")
    public DataSource twoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SpringLiquibase springLiquibaseOne() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:liquibase-changelog.xml");
        springLiquibase.setDataSource(oneDataSource());
        return springLiquibase;
    }

    @Bean
    public SpringLiquibase springLiquibaseTwo() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:liquibase-changelog.xml");
        springLiquibase.setDataSource(twoDataSource());
        return springLiquibase;
    }

    @Bean
    public DatebaseRoutingAspect datebaseRoutingAspect() {
        return new DatebaseRoutingAspect();
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }
}
