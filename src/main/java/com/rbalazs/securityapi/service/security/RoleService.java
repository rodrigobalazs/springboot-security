package com.rbalazs.securityapi.service.security;

import com.rbalazs.securityapi.model.Role;
import com.rbalazs.securityapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Role Service
 *
 * @author Rodrigo Balazs
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves a Role by the name given as parameter.
     *
     * @param name the role name to retrieve
     * @return a {@link Role}
     */
    public Role getRoleByName(final String name) {
        return roleRepository.findByName(name).orElse(null);
    }
}