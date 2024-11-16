package com.example.huseyinbabaltdd.services;

import com.example.huseyinbabaltdd.clients.PaymentClient;
import com.example.huseyinbabaltdd.dtos.OrderDto;
import com.example.huseyinbabaltdd.models.Order;
import com.example.huseyinbabaltdd.repositories.OrderRepository;
import com.example.huseyinbabaltdd.services.requests.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;

    public OrderDto createOrder(CreateOrderRequest request) {

        BigDecimal totalPrice = request.getUnitPrice().multiply(BigDecimal.valueOf(request.getAmount()));

        Order order = Order.builder()
                .totalPrice(totalPrice)
                .build();

        paymentClient.pay(order);

        Order savedOrder = orderRepository.save(order);

        return OrderDto.builder().id(savedOrder.getId()).totalPrice(totalPrice).build();
    }
}
