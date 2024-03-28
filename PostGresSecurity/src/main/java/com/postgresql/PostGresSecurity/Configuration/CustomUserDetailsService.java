package com.postgresql.PostGresSecurity.Configuration;

import com.postgresql.PostGresSecurity.Entities.User;
import com.postgresql.PostGresSecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException(("User does not exist! ")));
    }
}
