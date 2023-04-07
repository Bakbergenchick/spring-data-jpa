package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;
import com.spring.sdjpa.domain.Book;
import com.spring.sdjpa.repo.BookRepo;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDAOImpl implements BookDAO {
    private final EntityManagerFactory emf;
    private final BookRepo bookRepo;

    @Override
    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    @Override
    public Book getByISBN(String isbn) {
        EntityManager em = getEntityManager();

        try{
            TypedQuery<Book> query = em.createQuery("select a from Book a where a.isbn =: isbn",
                    Book.class);
            query.setParameter("isbn", isbn);

            Book book = query.getSingleResult();

            return book;
        }finally {
            em.close();
        }
    }

    @Override
    public Book getById(Long id) {
        return bookRepo.getById(id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepo.findBookByTitle(title)
                .orElseThrow(EntityNotFoundException::new);

    }

    @Override
    public Book saveNewBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Book foundBook = bookRepo.getById(book.getId());
        foundBook.setIsbn(book.getIsbn());
        foundBook.setPublisher(book.getPublisher());
        foundBook.setAuthorId(book.getAuthorId());
        foundBook.setTitle(book.getTitle());
        return bookRepo.save(foundBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepo.deleteById(id);
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
