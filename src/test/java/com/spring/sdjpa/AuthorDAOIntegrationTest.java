package com.spring.sdjpa;

import com.spring.sdjpa.dao.AuthorDAO;
import com.spring.sdjpa.dao.AuthorDAOImpl;
import com.spring.sdjpa.dao.BookDAO;
import com.spring.sdjpa.dao.BookDAOImpl;
import com.spring.sdjpa.domain.Author;
import com.spring.sdjpa.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@Import({AuthorDAOImpl.class, BookDAOImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDAOIntegrationTest {

    @Autowired
    AuthorDAO authorDAO;

    @Autowired
    BookDAO bookDAO;

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDAO.saveNewBook(book);

        bookDAO.deleteBookById(saved.getId());

        Book deleted = bookDAO.getById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setId(1L);
        Book saved = bookDAO.saveNewBook(book);

        saved.setTitle("New Book");
        bookDAO.updateBook(saved);

        Book fetched = bookDAO.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(1L);
        Book saved = bookDAO.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDAO.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        Book book = bookDAO.getById(3L);

        assertThat(book.getId()).isNotNull();
    }


    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDAO.saveAuthor(author);

        authorDAO.deleteAuthor(saved.getId());


        Author deleted = authorDAO.getById(saved.getId());
        assertThat(deleted).isNull();

        assertThat(authorDAO.getById(saved.getId()));

    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDAO.saveAuthor(author);

        saved.setLastName("Thompson");
        Author updated = authorDAO.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Thompson");
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");
        Author saved = authorDAO.saveAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDAO.getByNameAndSurname("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthor() {

        Author author = authorDAO.getById(1L);

        assertThat(author).isNotNull();

    }
}
