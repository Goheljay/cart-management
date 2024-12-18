package com.example.springboot3demo.serviceImpl;

import com.example.springboot3demo.dto.request.CreateProductDto;
import com.example.springboot3demo.modal.Product;
import com.example.springboot3demo.repository.ProductRepository;
import com.example.springboot3demo.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public void saveProduct(CreateProductDto createCouponsDto) {
        Product product = new Product();
        product.setPrice(createCouponsDto.getPrice());
        product.setName(createCouponsDto.getName());

        productRepository.save(product);
    }
}
