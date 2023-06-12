package com.example.service;

import com.example.model.Order;
import com.example.utils.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    public void placeOrder(Order order) throws JsonProcessingException {
        String uri = "http://localhost:8083/api/v1/orders";
        log.info("Placing order on product-id: {} for customer-id: {}",order.getProductId(),order.getCustomerId());
        new Helper().callApiToPost(uri,order);
    }
}
