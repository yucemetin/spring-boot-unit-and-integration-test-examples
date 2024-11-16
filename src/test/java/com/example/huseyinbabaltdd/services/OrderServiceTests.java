package com.example.huseyinbabaltdd.services;


import com.example.huseyinbabaltdd.clients.PaymentClient;
import com.example.huseyinbabaltdd.dtos.OrderDto;
import com.example.huseyinbabaltdd.models.Order;
import com.example.huseyinbabaltdd.repositories.OrderRepository;
import com.example.huseyinbabaltdd.services.requests.CreateOrderRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentClient paymentClient;

    public static Stream<Arguments> order_requests() {
        return Stream.of(
                Arguments.of("code1", 36, BigDecimal.valueOf(12.3), BigDecimal.valueOf(442.8)),
                Arguments.of("code2", 10, BigDecimal.valueOf(8), BigDecimal.valueOf(80)),
                Arguments.of("code2", 1110, BigDecimal.valueOf(8), BigDecimal.valueOf(8880))
        );
    }

    @ParameterizedTest
    @MethodSource("order_requests")
    public void it_should_create_orders(String productCode, Integer amount, BigDecimal unitPrice, BigDecimal totalPrice) {

        // given
        CreateOrderRequest request = CreateOrderRequest.builder()
                .productCode(productCode)
                .amount(amount)
                .unitPrice(unitPrice)
                .build();

        Order order = new Order();
        order.setId(3636L);

        when(orderRepository.save(any())).thenReturn(order);
        
        // when
        OrderDto orderDto = orderService.createOrder(request);

        // then
        then(orderDto.getTotalPrice()).isEqualTo(totalPrice);
    }

    @Test
    public void it_should_fail_order_creation_when_payment_failed() {
        
        // given
        CreateOrderRequest request = CreateOrderRequest.builder()
                .productCode("code1")
                .amount(3)
                .unitPrice(BigDecimal.valueOf(12))
                .build();

        doThrow(new IllegalArgumentException()).when(paymentClient).pay(any());

        // when
        Throwable throwable = catchThrowable(() -> orderService.createOrder(request));

        // then
        then(throwable).isInstanceOf(IllegalArgumentException.class);
        verifyNoInteractions(orderRepository);
    }
}
