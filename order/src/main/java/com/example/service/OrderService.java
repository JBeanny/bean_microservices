package com.example.service;

import com.example.model.OrderModel;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.util.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public List<OrderModel> getOrders(){
        return orderRepository.findAll();
    }

    public void save(OrderModel order) throws Exception {
        log.info("Checking product quantity ...");
        String urlToRequest = "http://localhost:8084/api/v1/products/" + order.getProductId();
        String response = new Helper().callApiToRetrieve(urlToRequest);
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(response,Product.class);

        if(product.getProductAmount() >= 1) {
            log.info("Product quantity sufficient ...");
            // in this operation we use kafka consume
            log.info("Grabbing product ...");

            OrderModel orderToSave = OrderModel.builder()
                    .customerId(order.getCustomerId())
                    .productId(order.getProductId())
                    .orderDate(LocalDateTime.now())
                    .build();
            log.info("Saving order ...");
            OrderModel savedOrder = orderRepository.save(orderToSave);
            kafkaTemplate.send("order-topic",savedOrder.toString());
        }else{
            log.info("Not enough amount ...");
            throw new Exception("Product amount insufficient");
        }

    }
}
