package com.example.service;

import com.example.config.RabbitConfig;
import com.example.dto.request.OrderCreateRequest;
import com.example.entity.Order;
import com.example.events.OrderCreatedEvent;
import com.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.UUID;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final AmqpTemplate amqpTemplate;

    public OrderService(OrderRepository orderRepository, AmqpTemplate amqpTemplate) {
        this.orderRepository = orderRepository;
        this.amqpTemplate = amqpTemplate;
    }

    @Transactional
    public void createOrder(OrderCreateRequest orderCreateRequest) {
        String product = orderCreateRequest.product();
        double amount = orderCreateRequest.amount();

        Order order = orderRepository.save(new Order(product, amount));
        long orderId = order.getId();

        String eventId = UUID.randomUUID().toString();
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(eventId, orderId, amount);
        amqpTemplate.convertAndSend(RabbitConfig.ORDER_QUEUE, orderCreatedEvent);
        log.info("Order created and order created event sent with order id: {}", orderId);
    }
}
