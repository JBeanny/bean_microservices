package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{product_id}")
    public Product getProductById(@PathVariable Long product_id) throws Exception {
        return productService.getProductById(product_id);
    }

    @PatchMapping("/{product_id}")
    public void updateProductAmount(@PathVariable Long product_id) {
        productService.updateProductAmount(product_id);
    }

    @PostMapping()
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        Map<String,Object> response = new HashMap<>();

        try{
            productService.save(product);
            response.put("status","success");
            response.put("message","successfully created product");

            return ResponseEntity.status(201).body(response);
        }catch(Exception ex) {
            response.put("status","fail");
            response.put("message",ex.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }
}
