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

        book.setAuthor(author);
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

        book.setAuthor(author);
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
    void testDeleteAuthor(){

        Author author = new Author();
        author.setFirstName("Aaron");
        author.setLastName("Paul");
        Author saveAuthor = authorDAO.saveAuthor(author);

        authorDAO.deleteAuthor(saveAuthor.getId());

        Author deleted = authorDAO.getById(saveAuthor.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void testUpdateAuthor(){

        Author author = new Author();
        author.setFirstName("Craig");
        author.setLastName("Walls");
        Author saveAuthor = authorDAO.saveAuthor(author);

        saveAuthor.setLastName("Wolter");
        Author updateAuthor = authorDAO.updateAuthor(saveAuthor);

        assertThat(updateAuthor.getLastName())
                .isEqualTo("Wolter");
    }

    @Test
    void testSaveAuthor(){

        Author author = new Author();
        author.setFirstName("Craig");
        author.setLastName("Walls");
        Author saveAuthor = authorDAO.saveAuthor(author);

        assertThat(saveAuthor).isNotNull();
    }

    @Test
    void testGetByID(){
        Author author = authorDAO.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetByNameAndSurname(){
        Author author = authorDAO.getByNameAndSurname("Craig", "Walls");
        assertThat(author).isNotNull();
    }
}
