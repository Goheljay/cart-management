package com.example.springboot3demo.controller;

import com.example.springboot3demo.dto.request.LoginDto;
import com.example.springboot3demo.dto.response.ApiResponse;
import com.example.springboot3demo.dto.response.ApiResponseDto;
import com.example.springboot3demo.dto.response.AuthDto;
import com.example.springboot3demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        String authToken = loginService.checkValidUserAndLogin(loginDto);
        AuthDto authDto = new AuthDto();
        authDto.setAccessToken(authToken);
        return new ResponseEntity<>(new ApiResponseDto(authDto,"Access token generated", HttpStatus.OK),HttpStatus.OK);
    }
}
