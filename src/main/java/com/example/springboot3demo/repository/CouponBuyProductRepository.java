package com.example.springboot3demo.repository;

import com.example.springboot3demo.modal.Coupon;
import com.example.springboot3demo.modal.CouponBuyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponBuyProductRepository extends JpaRepository<CouponBuyProduct, Long> {
    Optional<List<CouponBuyProduct>> findByCouponId(Coupon coupon);
}
