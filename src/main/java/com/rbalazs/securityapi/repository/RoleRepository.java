package com.rbalazs.securityapi.repository;

import com.rbalazs.securityapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> { }