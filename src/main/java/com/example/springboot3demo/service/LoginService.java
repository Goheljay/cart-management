package com.example.springboot3demo.service;

import com.example.springboot3demo.dto.request.LoginDto;

public interface LoginService {
    String checkValidUserAndLogin(LoginDto loginDto);
}
