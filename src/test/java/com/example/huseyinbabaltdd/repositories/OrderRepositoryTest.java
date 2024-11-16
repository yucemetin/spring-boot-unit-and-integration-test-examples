package com.example.huseyinbabaltdd.repositories;

import com.example.huseyinbabaltdd.models.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql");

    @Test
    public void it_should_find_orders() {

        // given
        Order order1 = Order.builder().totalPrice(BigDecimal.valueOf(123.22)).build();
        Order order2 = Order.builder().totalPrice(BigDecimal.valueOf(12)).build();
        Order order3 = Order.builder().totalPrice(BigDecimal.valueOf(35.3)).build();

        Object id1 = testEntityManager.persistAndGetId(order1);
        Object id2 = testEntityManager.persistAndGetId(order2);
        Object id3 = testEntityManager.persistAndGetId(order3);
        testEntityManager.flush();

        // when
        List<Order> orders = orderRepository.findAll();

        // then
        then(orders).isNotEmpty();

        Order o1 = orders.get(0);
        Order o2 = orders.get(1);
        Order o3 = orders.get(2);

        then(o1.getId()).isEqualTo(id1);
        then(o2.getId()).isEqualTo(id2);
        then(o3.getId()).isEqualTo(id3);

    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "create-drop");
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username",mysql::getUsername);
        registry.add("spring.datasource.password",mysql::getPassword);
        registry.add("spring.datasource.driver-class-name",mysql::getDriverClassName);
    }
}