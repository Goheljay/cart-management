package com.example.springboot3demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicableRespCouponsDto {
    private Long id;
    private String title;
    private String description;
    private String type;
    private Long discount;
}
