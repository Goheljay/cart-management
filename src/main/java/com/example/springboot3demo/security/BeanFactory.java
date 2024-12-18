package com.example.springboot3demo.security;

import com.example.springboot3demo.cutomException.CustomException;
import com.example.springboot3demo.modal.Users;
import com.example.springboot3demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BeanFactory {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return this::loadUserByUsername;
    }

    private UserDetails loadUserByUsername(String username) {
        Users byEmail = userRepository.findByEmail(username).orElseThrow(() -> new CustomException("User Does not exist", HttpStatus.FORBIDDEN));
        return new User(byEmail.getEmail(), byEmail.getPassword(), List.of());
    }
}
