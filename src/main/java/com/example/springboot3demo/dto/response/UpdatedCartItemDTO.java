package com.example.springboot3demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedCartItemDTO {
    private Long productId;
    private Long quantity;
    private Long price;

    // Getters and Setters
}

