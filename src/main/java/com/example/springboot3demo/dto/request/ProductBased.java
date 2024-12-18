package com.example.springboot3demo.dto.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBased {
    private String title;
    private List<Long> productId;
    private Long discount;
    private Long repetitionLimit;
    private String description;
}