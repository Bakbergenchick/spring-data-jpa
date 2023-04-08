package com.spring.sdjpa.paging;


import com.spring.sdjpa.dao.BookDAO;
import com.spring.sdjpa.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("local")
@ComponentScan("com.spring.sdjpa.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class JPATest {

    @Qualifier("bookDAOImpl")
    @Autowired
    BookDAO bookDao;

    @Test
    void findAllBooksPageableByTitle(){
        List<Book> books = bookDao.findAllBooksSortByTitle(PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("title"))));

        assertNotNull(books);
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void findAllBooksPageable1(){
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 10));

        assertNotNull(books);
        assertThat(books.size()).isEqualTo(10);
    }
}
