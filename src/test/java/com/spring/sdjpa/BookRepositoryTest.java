package com.spring.sdjpa;

import com.spring.sdjpa.domain.Book;
import com.spring.sdjpa.repo.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.spring.sdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    BookRepo bookRepo;

    @Test
    void testBookQueryJpaNamed(){
        Book book = bookRepo.jpaNamed("Clean Code");
        assertNotNull(book);
    }

    @Test
    void testBookQueryNative(){
        Book book = bookRepo.findBookByTitleWithQueryNative("Clean Code");
        assertNotNull(book);
    }

    @Test
    void testBookQueryNamed(){
        Book book = bookRepo.findBookByTitleWithQueryNamed("Clean Code");
        assertNotNull(book);
    }

    @Test
    void testBookQuery(){
        Book book = bookRepo.findBookByTitleWithQuery("Clean Code");
        assertNotNull(book);
    }

    @Test
    void testAsynchronus() throws ExecutionException, InterruptedException {
        Future<Book> bookFuture = bookRepo.queryByTitle("Clean Code");
        Book book = bookFuture.get();

        assertNotNull(book);
    }

    @Test
    void testEmptyResultException(){
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Book book = bookRepo.readByTitle("foob24");
        });
    }

    @Test
    void testForNullParam(){
        assertNull(bookRepo.getByTitle(null));
        assertNotNull(bookRepo.getByTitle("Domain-Driven Design"));
    }

}
