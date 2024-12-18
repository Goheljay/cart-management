package com.example.springboot3demo.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartBased {
    private String title;
    private Long threshold;
    private Long repetitionLimit;
    private String description;
    private Long discount;
}