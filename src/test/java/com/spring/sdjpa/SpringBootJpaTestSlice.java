package com.spring.sdjpa;

import com.spring.sdjpa.domain.Book;
import com.spring.sdjpa.repo.BookRepo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = {"com.spring.sdjpa.bootsrap"})
public class SpringBootJpaTestSlice {

    @Autowired
    private BookRepo bookRepo;

    @Commit
    @Order(1)
    @Test
    void testJpaTestSlice(){
        long countBefore = bookRepo.count();
        assertThat(countBefore).isEqualTo(2);
        Book book = new Book("Orwell", "234", "Amanat");
        bookRepo.save(book);

        long countAfter = bookRepo.count();

        assertThat(countBefore).isLessThan(countAfter);
    }

    @Order(2)
    @Test
    void testJpaTestSliceTransaction(){
        long countBefore = bookRepo.count();

        assertThat(countBefore).isEqualTo(3);
    }
}
