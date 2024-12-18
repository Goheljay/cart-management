package com.example.springboot3demo.service;

import com.example.springboot3demo.dto.request.ApplicableRequestDto;
import com.example.springboot3demo.dto.request.CreateCouponsDto;
import com.example.springboot3demo.dto.response.ApplicableRespCouponsDto;
import com.example.springboot3demo.dto.response.ApplyCouponResponseDTO;
import com.example.springboot3demo.dto.response.CouponDto;

import java.util.List;

public interface CouponService {
    void saveCoupon(CreateCouponsDto createCouponsDto);

    CouponDto getCouponById(Long id);

    List<CouponDto> getAllCoupon();

    List<ApplicableRespCouponsDto> getAllApplicableCoupons(ApplicableRequestDto applicableRequestDto);

    ApplyCouponResponseDTO getAllApplyCoupon(Long couponId, ApplicableRequestDto applicableRequestDto);
}
