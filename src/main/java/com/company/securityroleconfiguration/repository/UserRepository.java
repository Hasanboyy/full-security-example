package com.company.securityroleconfiguration.repository;

import com.company.securityroleconfiguration.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndEnabledIsTrue(String username);

    Boolean existsByUsernameAndEnabledIsTrue(String username);

    Optional<User> findByUsername(String username);

}
