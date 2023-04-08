package com.spring.sdjpa.paging;

import com.spring.sdjpa.dao.BookDAO;
import com.spring.sdjpa.dao.BookDAOHibernateImpl;
import com.spring.sdjpa.domain.Book;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan("com.spring.sdjpa.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HibernateDAOTest {

    @Autowired
    EntityManagerFactory emf;

    BookDAO bookDAO;

    @BeforeEach
    void setUp(){
        bookDAO = new BookDAOHibernateImpl(emf);
    }

    @Test
    void findAllBooks() {
        List<Book> books = bookDAO.findAllBooks(PageRequest.of(2, 5));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void findAllBooksByTitleSorting(){
        List<Book> books = bookDAO.findAllBooksSortByTitle(PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("title"))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

}
