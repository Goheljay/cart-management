package com.example.springboot3demo.service;


import com.example.springboot3demo.dto.request.CreateProductDto;

public interface ProductService {
    void saveProduct(CreateProductDto createCouponsDto);
}
