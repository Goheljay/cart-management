package com.example.springboot3demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedCartDTO {
    private List<UpdatedCartItemDTO> items;
    private double totalPrice;
    private double totalDiscount;
    private double finalPrice;

    // Getters and Setters
}
