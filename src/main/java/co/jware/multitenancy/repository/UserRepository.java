package co.jware.multitenancy.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import co.jware.multitenancy.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
