package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));
    }

    public void updateProductAmount(Long productId) {
        log.info("Finding product with id: {}",productId);
        Optional<Product> existedProduct = productRepository.findById(productId);

        if(existedProduct.isPresent()) {
            log.info("Product is found, grabbing product ...");
            Product productToUpdate = existedProduct.get();
            productToUpdate.setProductAmount(productToUpdate.getProductAmount() - 1);

            productRepository.save(productToUpdate);
            log.info("Grabbed ...");
        }else{
            log.info("Fail to grab the product ...");
        }
    }
}
