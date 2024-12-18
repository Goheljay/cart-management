package com.example.springboot3demo.dto.request;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponsDto {
    private String type;
    private ProductBased productBasedCoupon;
    private CartBased cartBasedCoupon;
    private BxGyBased bxGyBasedCoupon;
}







