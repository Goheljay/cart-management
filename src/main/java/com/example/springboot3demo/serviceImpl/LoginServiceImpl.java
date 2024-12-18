package com.example.springboot3demo.serviceImpl;

import com.example.springboot3demo.dto.request.LoginDto;
import com.example.springboot3demo.security.jwt.JwtProvider;
import com.example.springboot3demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    @Override
    public String checkValidUserAndLogin(LoginDto loginDto) {
        System.out.printf("qwerty :: ", loginDto.toString());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtProvider.generateToken(loginDto.getEmail());
        }
        return null;
    }
}
