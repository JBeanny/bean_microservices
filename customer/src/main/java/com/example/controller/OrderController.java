package com.example.controller;

import com.example.model.Order;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping("/place_order")
    public ResponseEntity<Object> placeOrder(@RequestBody Order order){
        Map<String,Object> response = new HashMap<>();

        try{
            orderService.placeOrder(order);
            response.put("status","success");
            response.put("message","Successfully placed order");

            return ResponseEntity.status(200).body(response);
        }catch (Exception exp) {
            response.put("status","fail");
            response.put("message",exp.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }
}
