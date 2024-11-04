package com.rbalazs.securityapi.repository;

import com.rbalazs.securityapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}