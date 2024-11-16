package com.example.huseyinbabaltdd.repositories;

import com.example.huseyinbabaltdd.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
