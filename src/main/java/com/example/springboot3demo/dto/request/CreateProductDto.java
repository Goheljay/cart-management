package com.example.springboot3demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.logging.LogManager;

@Data
@AllArgsConstructor
public class CreateProductDto {
    private String name;
    private Long price;
    private String description;
}
