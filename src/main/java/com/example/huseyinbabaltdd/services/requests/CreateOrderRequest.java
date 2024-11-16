package com.example.huseyinbabaltdd.services.requests;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    private String productCode;

    private Integer amount;

    private BigDecimal unitPrice;
}
