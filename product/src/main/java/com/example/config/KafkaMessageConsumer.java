package com.example.config;

import com.example.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
public class KafkaMessageConsumer {

    @Autowired
    private ProductService productService;

    @KafkaListener(topics = "order-topic", groupId = "product-service-group")
    public void updateProductAmount(String message) {
        log.info("Message Consumer: {}",message);
        int startIndex = message.indexOf("productId=") + 10;
        int endIndex = message.indexOf(",", startIndex);
        String productIdStr = message.substring(startIndex, endIndex);
        Long productId = Long.parseLong(productIdStr);
        log.info("Getting product ID: {}",productId);
        productService.updateProductAmount(productId);
        log.info("Product is grabbed");
    }
}
