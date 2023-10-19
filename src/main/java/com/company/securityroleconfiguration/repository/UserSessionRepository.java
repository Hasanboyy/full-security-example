package com.company.securityroleconfiguration.repository;

import com.company.securityroleconfiguration.module.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
