package com.company.qldp.userservice.domain.repository;

import com.company.qldp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);

    User findByKeycloakUid(String uid);
}

