package com.example.springboot3demo.repository;

import com.example.springboot3demo.modal.Coupon;
import com.example.springboot3demo.modal.CouponGetProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponGetProductRepository extends JpaRepository<CouponGetProduct, Long> {
    Optional<List<CouponGetProduct>> findByCouponId(Coupon coupon);
}
