package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;
import com.spring.sdjpa.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDAOImpl implements BookDAO {
    private final EntityManagerFactory emf;

    @Override
    public List<Book> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> books = em.createNamedQuery("find_all_books", Book.class);
            return books.getResultList();
        }finally {
            em.close();
        }

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
        EntityManager em = getEntityManager();
        Book book = getEntityManager().find(Book.class, id);
        em.close();
        return book;
    }

    @Override
    public Book findBookByTitle(String title) {
        EntityManager em = getEntityManager();
//        TypedQuery<Book> query = em
//                .createQuery("SELECT b FROM Book b where b.title = :title", Book.class);
        Book book;
        try {
            TypedQuery<Book> query = em.createNamedQuery("find_by_title", Book.class);
            query.setParameter("title", title);
            book = query.getSingleResult();
            return book;
        } finally {
            em.close();
        }

    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        Book savedBook = em.find(Book.class, book.getId());
        em.getTransaction().commit();
        em.close();
        return savedBook;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.getTransaction().commit();
        em.close();
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
