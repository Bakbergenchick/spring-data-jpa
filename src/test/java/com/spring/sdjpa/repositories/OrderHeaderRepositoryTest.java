package com.spring.sdjpa.repositories;

import com.spring.sdjpa.domain.*;
import com.spring.sdjpa.repo.OrderHeaderRepository;
import com.spring.sdjpa.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

//    @Autowired
//    OrderApprovalRepository approvalRepository;

    Product product;

    @BeforeEach
    void setUp() {
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("Sefsefefesf");

        product = productRepository.saveAndFlush(newProduct);

    }

    @Test
    void testSaveOrder(){
        OrderHeader orderHeader = new OrderHeader();
//        orderHeader.setCustomer("New Customer");
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getUpdatedDate());
    }

    @Test
    void testSaveOrder2(){
        OrderHeader orderHeader = new OrderHeader();

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");
        orderApproval.setOrderHeader(orderHeader);
        orderHeader.setOrderApproval(orderApproval);

        orderHeader.addOrderlinetToHeader(orderLine);

        OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();


        assertThrows(NoSuchElementException.class, () -> {
            Optional<OrderHeader> fetchedOrder = orderHeaderRepository.findById(savedOrder.getId());

            assertNull(fetchedOrder.get());
        });
    }

}
