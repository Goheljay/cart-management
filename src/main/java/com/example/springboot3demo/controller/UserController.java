package com.example.springboot3demo.controller;

import com.example.springboot3demo.dto.request.SignUpUserDto;
import com.example.springboot3demo.dto.response.ApiResponse;
import com.example.springboot3demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse registerUser(@RequestBody SignUpUserDto signUpUserDto) {
        Long userId = userService.registerUser(signUpUserDto);
        return new ApiResponse(HttpStatus.OK, new ApiResponse(HttpStatus.OK, "User Saved", userId));
    }
}
