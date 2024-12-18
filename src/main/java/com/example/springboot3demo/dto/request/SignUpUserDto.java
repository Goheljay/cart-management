package com.example.springboot3demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUserDto {
    private String name;
    private String email;
    private String password;
}
