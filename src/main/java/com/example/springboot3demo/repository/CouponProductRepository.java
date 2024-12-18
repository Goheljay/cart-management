package com.example.springboot3demo.repository;

import com.example.springboot3demo.modal.Coupon;
import com.example.springboot3demo.modal.CouponBuyProduct;
import com.example.springboot3demo.modal.CouponProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponProductRepository extends JpaRepository<CouponProductMapping, Long> {
    Optional<CouponBuyProduct> findByCouponId(Coupon coupon);
}
