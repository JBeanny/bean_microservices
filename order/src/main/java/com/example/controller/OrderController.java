package com.example.controller;

import com.example.model.OrderModel;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping()
    public List<OrderModel> getOrders(){
        return orderService.getOrders();
    }

    @PostMapping()
    public void createOrder(@RequestBody OrderModel order) throws Exception {
        try{
            orderService.save(order);
            log.info("Placed order...");
        }catch (Exception exp) {
            log.info(exp.getMessage());
        }
    }


}
