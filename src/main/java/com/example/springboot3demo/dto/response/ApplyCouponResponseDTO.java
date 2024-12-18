package com.example.springboot3demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyCouponResponseDTO {
    private UpdatedCartDTO updatedCart;

    // Getters and Setters
}
