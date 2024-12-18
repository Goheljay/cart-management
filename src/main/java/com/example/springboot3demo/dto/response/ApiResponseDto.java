package com.example.springboot3demo.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ApiResponseDto {
    private Object data;
    private String message;
    private HttpStatus status;

    public ApiResponseDto(Object data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
