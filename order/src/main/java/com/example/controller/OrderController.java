package com.example.controller;

import com.example.model.OrderModel;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping()
    public List<OrderModel> getOrders(){
        return orderService.getOrders();
    }

    @PostMapping()
    public void createOrder(@RequestBody OrderModel order) {
        orderService.save(order);
    }


}
