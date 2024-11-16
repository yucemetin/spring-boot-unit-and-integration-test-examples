package com.example.huseyinbabaltdd.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderDto {

    private Long id;
    private BigDecimal totalPrice;
}
