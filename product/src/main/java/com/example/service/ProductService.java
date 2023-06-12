package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        Product productToSave = Product.builder()
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productBrand(product.getProductBrand())
                .productAmount(product.getProductAmount())
                .build();
        log.info("Saving product: {}",productToSave.getId());
        productRepository.save(productToSave);
    }
}
