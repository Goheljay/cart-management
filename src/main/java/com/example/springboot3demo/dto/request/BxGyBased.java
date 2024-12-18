package com.example.springboot3demo.dto.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BxGyBased {
    private String title;
    private List<GetProductDto> getProducts;
    private List<BuyProductDto> buyProducts;
    private String description;
    private Long repetitionLimit;
}
