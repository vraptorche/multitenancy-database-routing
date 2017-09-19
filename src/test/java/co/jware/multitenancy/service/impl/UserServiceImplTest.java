package co.jware.multitenancy.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import co.jware.multitenancy.MultiTenancyDatabaseRoutingConfig;
import co.jware.multitenancy.domain.User;
import co.jware.multitenancy.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@RunWith(SpringRunner.class)
@DataJpaTest(excludeAutoConfiguration = {DataSourceAutoConfiguration.class, LiquibaseAutoConfiguration.class})
@SpringBootTest(classes = MultiTenancyDatabaseRoutingConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableAspectJAutoProxy
@Transactional
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    @Rollback(false)
    public void createUser_two() throws Exception {
        User user = userService.createUser("john_doe", "TWO");
        assertThat(user).isNotNull();
    }

    @Test
    @Rollback(false)
    public void createUser_one() throws Exception {
        User user = userService.createUser("jane_doe", "ONE");
        assertThat(user).isNotNull();
    }
}