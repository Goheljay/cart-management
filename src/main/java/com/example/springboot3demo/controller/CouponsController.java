package com.example.springboot3demo.controller;

import com.example.springboot3demo.dto.request.ApplicableRequestDto;
import com.example.springboot3demo.dto.request.CreateCouponsDto;
import com.example.springboot3demo.dto.request.CreateProductDto;
import com.example.springboot3demo.dto.response.*;
import com.example.springboot3demo.service.CouponService;
import com.example.springboot3demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CouponsController {

    private final CouponService couponService;
    private final ProductService productService;

    public CouponsController(CouponService couponService, ProductService productService) {
        this.couponService = couponService;
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody CreateProductDto createProductDto) {
        productService.saveProduct(createProductDto);
        return new ResponseEntity<>(new ApiResponse( HttpStatus.OK, "Product Saved", null), HttpStatus.CREATED);
    }

    @PostMapping("/coupons")
    public ResponseEntity<ApiResponse> createCoupon(@RequestBody CreateCouponsDto createCouponsDto) {
        couponService.saveCoupon(createCouponsDto);
        return new ResponseEntity<>(new ApiResponse( HttpStatus.OK, "Coupon Saved", null), HttpStatus.CREATED);
    }

    @GetMapping(value = "/coupons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseDto getCoupons(@PathVariable("id") Long id) {
        CouponDto coupon = couponService.getCouponById(id);
        return new ApiResponseDto(coupon, "Coupon Saved", HttpStatus.OK);
    }

    @GetMapping(value = "/coupons", produces = "application/json")
    public ResponseEntity<ApiResponseDto> getAllCoupons() {
        List<CouponDto> coupon = couponService.getAllCoupon();
        return new ResponseEntity<>(new ApiResponseDto(coupon, "All Coupons Retrieved", HttpStatus.OK), HttpStatus.OK);
    }
    @PostMapping("/applicable-coupons")
    public ResponseEntity<ApiResponseDto> getAllCoupons(@RequestBody ApplicableRequestDto applicableRequestDto) {
        List<ApplicableRespCouponsDto> coupon = couponService.getAllApplicableCoupons(applicableRequestDto);
        return new ResponseEntity<>(new ApiResponseDto(coupon, "All Coupons Retrieved", HttpStatus.OK), HttpStatus.OK);
    }
    @PostMapping("/apply-coupon/{id}")
    public ResponseEntity<ApiResponseDto> applyCoupon(@PathVariable("id")Long couponId, @RequestBody ApplicableRequestDto applicableRequestDto) {
        ApplyCouponResponseDTO coupon = couponService.getAllApplyCoupon(couponId,applicableRequestDto);
        return new ResponseEntity<>(new ApiResponseDto(coupon, "All Coupons Retrieved", HttpStatus.OK), HttpStatus.OK);
    }
}
