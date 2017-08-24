package co.jware.multitenancy.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.jware.multitenancy.domain.User;
import co.jware.multitenancy.repository.UserRepository;
import co.jware.multitenancy.service.UserService;
import co.jware.multitenancy.util.DatabaseRouting;
import co.jware.multitenancy.util.TenantId;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {this.repository = repository;}

    @Override
    public User createUser(String username) {
        return null;
    }

    @Override
    @DatabaseRouting
    public User createUser(String userName, @TenantId String tenantId) {
        return null;
    }
}
