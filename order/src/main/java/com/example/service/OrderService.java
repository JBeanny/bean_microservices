package com.example.service;

import com.example.model.OrderModel;
import com.example.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderModel> getOrders(){
        return orderRepository.findAll();
    }

    public void save(OrderModel order) {
        OrderModel orderToSave = OrderModel.builder()
                .customerId(order.getCustomerId())
                .productId(order.getProductId())
                .orderDate(LocalDateTime.now())
                .build();
        log.info("Saving order: {}",order.getId());
        orderRepository.save(orderToSave);
    }
}
