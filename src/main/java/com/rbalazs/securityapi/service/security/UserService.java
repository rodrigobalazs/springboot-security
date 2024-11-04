package com.rbalazs.securityapi.service.security;

import com.rbalazs.securityapi.enums.AppValidations;
import com.rbalazs.securityapi.exception.CustomException;
import com.rbalazs.securityapi.model.Role;
import com.rbalazs.securityapi.model.User;
import com.rbalazs.securityapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User Service
 *
 * @author Rodrigo Balazs
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository, final RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bcryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Retrieves a User by the email address given as parameter.
     *
     * @param email the email address
     * @return a {@link User}
     */
    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Creates/Saves a new User into the repository.
     *
     * @param email the user email address
     * @param password the user password
     * @param roleName the assigned role
     *
     * @return a {@link User} with the persisted user
     */
    public User create(final String email, final String password, final String roleName) {

        if (getUserByEmail(email) != null) {
            throw new CustomException(AppValidations.USER_ALREADY_EXIST);
        }

        Role role = roleService.getRoleByName(roleName);
        String encodedPassword = bcryptPasswordEncoder.encode(password);
        User user = new User(email,encodedPassword, role);
        userRepository.save(user);
        return user;
    }
}