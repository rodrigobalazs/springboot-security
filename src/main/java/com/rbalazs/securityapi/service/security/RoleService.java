package com.rbalazs.securityapi.service.security;

import com.rbalazs.securityapi.model.Role;
import com.rbalazs.securityapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Role Service
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(final String name) {
        return roleRepository.findByName(name).orElse(null);
    }
}