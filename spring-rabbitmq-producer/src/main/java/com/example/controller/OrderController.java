package com.example.controller;

import com.example.dto.request.OrderCreateRequest;
import com.example.service.OrderService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostConstruct
    public void run(){
        orderService.createOrder(new OrderCreateRequest("i phone 15",1_50_000));
        orderService.createOrder(new OrderCreateRequest("i phone 10",70_000));
    }

    @PostMapping("/")
    public ResponseEntity<Void> createOrder(
            @RequestBody OrderCreateRequest orderCreateRequest) {

        orderService.createOrder(orderCreateRequest);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
