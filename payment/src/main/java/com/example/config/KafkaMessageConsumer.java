package com.example.config;

import com.example.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
public class KafkaMessageConsumer {

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "order-topic", groupId = "payment-service-group")
    public void updateProductAmount(String message) throws JsonProcessingException {
        try{
            log.info("Message Consumer: {}",message);
            // get orderId
            int orderStartIndex = message.indexOf("id=") + 3;
            int orderEndIndex = message.indexOf(",", orderStartIndex);
            String orderIdStr = message.substring(orderStartIndex, orderEndIndex);
            Long orderId = Long.parseLong(orderIdStr);
            log.info("Getting order ID: {}",orderId);
            // get productId
            int productStartIndex = message.indexOf("productId=") + 10;
            int productEndIndex = message.indexOf(",", productStartIndex);
            String productIdStr = message.substring(productStartIndex, productEndIndex);
            Long productId = Long.parseLong(productIdStr);
            log.info("Getting product ID: {}",productId);

            paymentService.save(orderId,productId);

            log.info("Payment is created");

        }catch(Exception ex) {
            log.info("Failed to create payment");
            log.info(ex.getMessage());
        }
    }
}
