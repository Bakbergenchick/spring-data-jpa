package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;
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
public class AuthorDAOImpl implements AuthorDAO {

    private final EntityManagerFactory emF;

    private EntityManager getEntityManager() {
        return emF.createEntityManager();
    }


    @Override
    public List<Author> findAll() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Author> authors = em.createNamedQuery("author_findAll", Author.class);

            return authors.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager em = getEntityManager();

        try{
            Query query = em.createQuery("select a from Author a where a.lastName like :last_name");
            query.setParameter("last_name", lastName + "%");
            List<Author> result =  query.getResultList();

            return result;
        }finally {
            em.close();
        }
    }

    @Override
    public Author getById(Long id) {
        EntityManager em = getEntityManager();
        Author author = getEntityManager().find(Author.class, id);
        em.close();
        return author;
    }

    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        try {
            Query nativeQuery = em.createNativeQuery("select * from author a where a.first_name = ? and a.last_name = ?", Author.class);
            nativeQuery.setParameter(1, firstName);
            nativeQuery.setParameter(2, lastName);

            return (Author) nativeQuery.getSingleResult();
        }finally {
            em.close();
        }
    }

    @Override
    public Author getByNameAndSurname(String name, String surname) {
        EntityManager em = getEntityManager();
//        TypedQuery<Author> query = em.createQuery("select a from Author a " +
//                "where a.firstName =: first_name and a.lastName =: last_name", Author.class);
        try {
            TypedQuery<Author> query = em.createNamedQuery("author_ByNameAndSurname", Author.class);
            query.setParameter("first_name", name);
            query.setParameter("last_name", surname);
            Author author = query.getSingleResult();

            return author;
        }finally {
            em.close();
        }

    }

    @Override
    public Author saveAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager em = getEntityManager();


        try {
            em.joinTransaction();
            em.merge(author);
            em.flush();
            em.clear();
            Author updAuthor = em.find(Author.class, author.getId());
            return updAuthor;
        } finally {
            em.close();
        }

    }

    @Override
    public void deleteAuthor(Long id) {

        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            Author author = em.find(Author.class, id);
            em.remove(author);
            em.flush();
            em.getTransaction().commit();
        }
    }
}
