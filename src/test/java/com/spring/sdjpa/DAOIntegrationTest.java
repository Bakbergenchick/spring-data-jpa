package com.spring.sdjpa;

import com.spring.sdjpa.dao.AuthorDAO;
import com.spring.sdjpa.dao.AuthorDAOImpl;
import com.spring.sdjpa.dao.BookDAO;
import com.spring.sdjpa.dao.BookDAOImpl;
import com.spring.sdjpa.domain.Author;
import com.spring.sdjpa.domain.Book;
import jakarta.persistence.EntityNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@Import({AuthorDAOImpl.class, BookDAOImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DAOIntegrationTest {

    @Autowired
    AuthorDAO authorDAO;

    @Qualifier("bookDAOImpl")
    @Autowired
    BookDAO bookDAO;

    @Test
    void testFindBookByTitle() {
        Book book = new Book();
        book.setIsbn("1235" + RandomString.make());
        book.setTitle("TITLETEST2");

        Book saved = bookDAO.saveNewBook(book);

        Book fetched = bookDAO.findBookByTitle(book.getTitle());
        assertThat(fetched).isNotNull();

        bookDAO.deleteBookById(saved.getId());
    }
    @Test
    void testFindAllBooks() {
        List<Book> books = bookDAO.findAll();

        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }


    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDAO.saveNewBook(book);

        bookDAO.deleteBookById(saved.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            bookDAO.getById(saved.getId());
        });
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);
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
    void testFindAll(){
        List<Author> authors = authorDAO.findAll();
        assertThat(authors).isNotNull();
    }

    @Test
    void testlistAuthorByLastNameLike(){
        List<Author> authors = authorDAO.listAuthorByLastNameLike("Wall");
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDAO.saveAuthor(author);

        authorDAO.deleteAuthor(saved.getId());


        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            Author deleted = authorDAO.getById(saved.getId());
        });

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
    void testGetAuthorByNameNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            Author author = authorDAO.getByNameAndSurname("foo", "bar");
        });
    }

    @Test
    void testGetAuthorByNameNative() {
        Author author = authorDAO.findAuthorByNameNative("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthor() {

        Author author = authorDAO.getById(1L);

        assertThat(author).isNotNull();

    }
}
