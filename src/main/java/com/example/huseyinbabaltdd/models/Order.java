package com.example.huseyinbabaltdd.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private BigDecimal totalPrice;

}
