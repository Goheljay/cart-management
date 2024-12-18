package com.example.springboot3demo.service;

import com.example.springboot3demo.dto.request.SignUpUserDto;

public interface UserService {
    Long registerUser(SignUpUserDto signUpUserDto);
}
