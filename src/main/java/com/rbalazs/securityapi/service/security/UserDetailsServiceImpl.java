package com.rbalazs.securityapi.service.security;

import com.rbalazs.securityapi.model.User;
import com.rbalazs.securityapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User´s Details Service implementation of core spring security service {@link UserDetailsService}
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Whenever a given User sign-in into the App, Spring Security loads the User´s details
     */
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }
}
