package com.example.service;

import com.example.Repository.PaymentRepository;
import com.example.model.Payment;
import com.example.model.Product;
import com.example.util.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    public void save(Long orderId,Long productId) throws JsonProcessingException {
        String productUrl = "http://localhost:8084/api/v1/products/" + productId;
        String productStr = new Helper().callApiToRetrieve(productUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productStr,Product.class);

        Payment paymentToSave = Payment.builder()
                .orderId(orderId)
                .totalPrice(product.getProductPrice())
                .createDate(LocalDateTime.now())
                .expireData(LocalDateTime.now().plusDays(7))
                .build();
        paymentRepository.save(paymentToSave);
    }
}
