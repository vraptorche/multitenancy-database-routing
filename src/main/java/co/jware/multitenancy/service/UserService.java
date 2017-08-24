package co.jware.multitenancy.service;

import co.jware.multitenancy.domain.User;
import co.jware.multitenancy.util.TenantId;

public interface UserService {

    User createUser(String username);

    User createUser(String userName, @TenantId String tenantId);
}
