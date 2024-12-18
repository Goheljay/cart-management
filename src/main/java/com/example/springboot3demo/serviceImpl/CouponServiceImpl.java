package com.example.springboot3demo.serviceImpl;

import com.example.springboot3demo.cutomException.CustomException;
import com.example.springboot3demo.dto.request.*;
import com.example.springboot3demo.dto.response.*;
import com.example.springboot3demo.modal.*;
import com.example.springboot3demo.repository.*;
import com.example.springboot3demo.service.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final ProductRepository productRepository;
    private final CouponProductRepository couponProductRepository;
    private final CouponBuyProductRepository couponBuyProductRepository;
    private final CouponGetProductRepository couponGetProductRepository;

    public CouponServiceImpl(CouponRepository couponRepository, ProductRepository productRepository, CouponProductRepository couponProductRepository, CouponBuyProductRepository couponBuyProductRepository, CouponGetProductRepository couponGetProductRepository) {
        this.couponRepository = couponRepository;
        this.productRepository = productRepository;
        this.couponProductRepository = couponProductRepository;
        this.couponBuyProductRepository = couponBuyProductRepository;
        this.couponGetProductRepository = couponGetProductRepository;
    }

    @Override
    public void saveCoupon(CreateCouponsDto createCouponsDto) {
        if (Objects.equals(createCouponsDto.getType(), "productBasedCoupon")) {
            createCouponsDto.getProductBasedCoupon().getProductId().forEach(product -> {
                ProductBased productBasedCoupon = createCouponsDto.getProductBasedCoupon();

                Product productById = getProductFromId(product);
                Coupon coupon = new Coupon();
                coupon.setType(createCouponsDto.getType());
                coupon.setDescription(productBasedCoupon.getDescription());
                coupon.setDiscount(productBasedCoupon.getDiscount());
                coupon.setName(productBasedCoupon.getTitle());
                coupon.setRepetitionLimit(productBasedCoupon.getRepetitionLimit());
                coupon.setStatus(true);
                Coupon savedCoupon = couponRepository.save(coupon);


                CouponProductMapping couponProductMapping = new CouponProductMapping();
                couponProductMapping.setCouponId(savedCoupon);
                couponProductMapping.setProductId(productById);
                couponProductRepository.save(couponProductMapping);

//            SaveProductBaseCouponDto saveProductBaseCouponDto = new SaveProductBaseCouponDto();
//            saveProductBaseCouponDto.setId(savedCoupon.getId());
//            saveProductBaseCouponDto.setName(savedCoupon.getName());

            });
        } else if (Objects.equals(createCouponsDto.getType(), "cart-wise")) {
            Coupon coupon = getCoupon(createCouponsDto);
            couponRepository.save(coupon);
        } else if (Objects.equals(createCouponsDto.getType(), "BxGyBasedCoupon")) {

            BxGyBased bxGyBasedCoupon = createCouponsDto.getBxGyBasedCoupon();

            Coupon coupon = new Coupon();
            coupon.setType(createCouponsDto.getType());
            coupon.setName(bxGyBasedCoupon.getTitle());
            coupon.setDescription(bxGyBasedCoupon.getDescription());
            coupon.setRepetitionLimit(bxGyBasedCoupon.getRepetitionLimit());
            coupon.setStatus(true);
            Coupon save = couponRepository.save(coupon);

            bxGyBasedCoupon.getBuyProducts().forEach(buyProduct -> {
                Product productFromId = getProductFromId(buyProduct.getProductId());
                CouponBuyProduct couponBuyProduct = new CouponBuyProduct();
                couponBuyProduct.setCouponId(save);
                couponBuyProduct.setProductId(productFromId);
                couponBuyProduct.setQuantity(buyProduct.getQuantity());
                couponBuyProductRepository.save(couponBuyProduct);
            });

            bxGyBasedCoupon.getGetProducts().forEach(getProductDto -> {
                Product productFromId = getProductFromId(getProductDto.getProductId());
                CouponGetProduct couponGetProduct = new CouponGetProduct();
                couponGetProduct.setCouponId(save);
                couponGetProduct.setProductId(productFromId);
                couponGetProduct.setQuantity(getProductDto.getQuantity());
                couponGetProductRepository.save(couponGetProduct);
            });
        }
    }

    private static Coupon getCoupon(CreateCouponsDto createCouponsDto) {
        CartBased cartBasedCoupon = createCouponsDto.getCartBasedCoupon();
        Coupon coupon = new Coupon();
        coupon.setType(createCouponsDto.getType());
        coupon.setThreshold(cartBasedCoupon.getThreshold());
        coupon.setName(cartBasedCoupon.getTitle());
        coupon.setDescription(cartBasedCoupon.getDescription());
        coupon.setDiscount(cartBasedCoupon.getDiscount());
        coupon.setStatus(true);
        coupon.setRepetitionLimit(cartBasedCoupon.getRepetitionLimit());
        return coupon;
    }

    @Override
    public CouponDto getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow();
        CouponDto couponDto = new CouponDto();
        couponDto.setId(coupon.getId());
        couponDto.setName(coupon.getName());
        couponDto.setDescription(coupon.getDescription());
        couponDto.setDiscount(coupon.getDiscount());
        couponDto.setType(coupon.getType());
        return couponDto;
    }

    @Override
    public List<CouponDto> getAllCoupon() {
        List<Coupon> allCoupons = couponRepository.findAllByStatusTrue().orElseThrow(() -> new CustomException("Coupon not found", HttpStatus.BAD_REQUEST));
        return allCoupons.stream()
                .map(coupon -> {
                    CouponDto dto = new CouponDto();
                    dto.setId(coupon.getId());
                    dto.setType(coupon.getType());
                    dto.setDescription(coupon.getDescription());
                    dto.setDiscount(coupon.getDiscount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicableRespCouponsDto> getAllApplicableCoupons(ApplicableRequestDto applicableRequestDto) {
        List<Coupon> allCoupons = couponRepository.findAllByStatusTrue().orElseThrow(() -> new CustomException("Coupon not found", HttpStatus.BAD_REQUEST));
        double totalCartValue = applicableRequestDto.getCart().getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();

        List<ApplicableRespCouponsDto> applicableCoupons = new ArrayList<>();

        for (Coupon coupon : allCoupons) {
            if (coupon.getType().equals("cart-wise")) {
                // Check if the cart meets the minimum value
                Long minCartValue = coupon.getThreshold();
                if (totalCartValue >= minCartValue) {
                    double discount = (totalCartValue * coupon.getDiscount()) / 100;
                    applicableCoupons.add(new ApplicableRespCouponsDto(coupon.getId(), coupon.getName(), coupon.getDescription(), coupon.getType(), (long) discount));
                }
            } else if (coupon.getType().equals("BxGyBasedCoupon")) {
                // Check if the "buy" condition is met
                boolean canApply = true;

                // Validate buy products
                List<CouponBuyProduct> buyProducts = couponBuyProductRepository.findByCouponId(coupon).orElseThrow(() -> new CustomException("No Coupon Found", HttpStatus.OK));
                Long repetitionLimit = coupon.getRepetitionLimit();

                for (CouponBuyProduct buyProduct : buyProducts) {
                    Long productId = buyProduct.getProductId().getId();
                    Long requiredQuantity = buyProduct.getQuantity() * repetitionLimit;

                    Optional<Item> firstItem = applicableRequestDto.getCart().getItems().stream()
                            .filter(item -> item.getProductId() == productId)
                            .findFirst();

                    if (firstItem.isEmpty() || firstItem.get().getQuantity() < requiredQuantity) {
                        canApply = false;
                        break;
                    }
                }

                if (canApply) {
                    // Calculate discount for "get" products
                    double discount = 0;
                    List<CouponGetProduct> couponBuyProducts = couponGetProductRepository.findByCouponId(coupon).orElseThrow(() -> new CustomException("No Coupon Found", HttpStatus.OK));

                    for (CouponGetProduct getProduct : couponBuyProducts) {
                        Long productId = getProduct.getProductId().getId();
                        Long freeQuantity = getProduct.getQuantity() * repetitionLimit;

                        Optional<Item> cartItem = applicableRequestDto.getCart().getItems().stream()
                                .filter(item -> item.getProductId() == productId)
                                .findFirst();

                        if (cartItem.isPresent()) {
                            discount += freeQuantity * getProduct.getProductId().getPrice();
                        }
                    }
                    applicableCoupons.add(new ApplicableRespCouponsDto(coupon.getId(), coupon.getName(), coupon.getDescription(), coupon.getType(), (long) discount));
                }
            }
        }


        return applicableCoupons;
    }

    @Override
    public ApplyCouponResponseDTO getAllApplyCoupon(Long couponId, ApplicableRequestDto request) {

        List<Item> items = request.getCart().getItems();

        Long totalDiscount = 0L;

        List<UpdatedCartItemDTO> updatedItems = new ArrayList<>();

        List<Long> productPrice = new ArrayList<>();
        items.forEach(prodcuct -> {
            productPrice.add(prodcuct.getProductId());
        });
        long totalPriceOfCart = request.getCart().getItems().stream().mapToLong(product -> product.getPrice() * product.getQuantity()).sum();

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CustomException("Coupon Not Found", HttpStatus.BAD_REQUEST));

        switch (coupon.getType()) {
            case "BxGyBasedCoupon":
                boolean canApply = true;

                List<CouponBuyProduct> buyProducts = couponBuyProductRepository.findByCouponId(coupon).orElseThrow(() -> new CustomException("No Coupon Found", HttpStatus.OK));
                Long repetitionLimit = coupon.getRepetitionLimit();

                for (CouponBuyProduct buyProduct : buyProducts) {
                    Long productId = buyProduct.getProductId().getId();
                    Long requiredQuantity = buyProduct.getQuantity() * repetitionLimit;

                    Optional<Item> firstItem = request.getCart().getItems().stream()
                            .filter(item -> item.getProductId() == productId)
                            .findFirst();

                    if (firstItem.isEmpty() || firstItem.get().getQuantity() < requiredQuantity) {
                        canApply = false;
                        break;
                    }
                }


                if (canApply) {
                    long discount = 0L;
                    List<CouponGetProduct> couponBuyProducts = couponGetProductRepository.findByCouponId(coupon).orElseThrow(() -> new CustomException("No Coupon Found", HttpStatus.OK));

                    for (CouponGetProduct getProduct : couponBuyProducts) {
                        Long productId = getProduct.getProductId().getId();
                        Long freeQuantity = getProduct.getQuantity() * repetitionLimit;

                        Optional<Item> cartItem = request.getCart().getItems().stream()
                                .filter(item -> item.getProductId() == productId)
                                .findFirst();

                        if (cartItem.isPresent()) {
                            discount += freeQuantity * getProduct.getProductId().getPrice();
                        }
                    }
                    totalDiscount = discount;
                } else {
                    throw new CustomException("Coupon Not valid", HttpStatus.BAD_REQUEST);
                }
                break;

            case "cart-wise":
                if (coupon.getThreshold() <= totalPriceOfCart) {
                    totalDiscount = coupon.getDiscount();
                } else {
                    throw new CustomException("Coupon Not valid", HttpStatus.BAD_REQUEST);
                }
                break;

            case "productBasedCoupon":
                if (request.getCart().getItems().size() > 1) {
                    throw new CustomException("Coupon Not valid", HttpStatus.BAD_REQUEST);
                }
                CouponBuyProduct byCouponId = couponProductRepository.findByCouponId(coupon).orElseThrow(() -> new CustomException("",HttpStatus.OK));
                Long quantity = byCouponId.getQuantity();
                if (request.getCart().getItems().get(0).getQuantity() >= quantity) {
                    totalDiscount = quantity;
                }

        }

        for (Item item : items) {

            UpdatedCartItemDTO updatedItem = new UpdatedCartItemDTO();
            updatedItem.setProductId(item.getProductId());
            updatedItem.setQuantity(item.getQuantity());
            updatedItem.setPrice(item.getPrice());
            updatedItems.add(updatedItem);
        }

        long finalPrice = totalPriceOfCart - totalDiscount;

        UpdatedCartDTO updatedCart = new UpdatedCartDTO();
        updatedCart.setItems(updatedItems);
        updatedCart.setTotalPrice(totalPriceOfCart);
        updatedCart.setTotalDiscount(totalDiscount);
        updatedCart.setFinalPrice(finalPrice);

        ApplyCouponResponseDTO applyCouponResponseDTO = new ApplyCouponResponseDTO();
        applyCouponResponseDTO.setUpdatedCart(updatedCart);

        return applyCouponResponseDTO;
    }

    private Product getProductFromId(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found", HttpStatus.BAD_REQUEST));
    }
}
