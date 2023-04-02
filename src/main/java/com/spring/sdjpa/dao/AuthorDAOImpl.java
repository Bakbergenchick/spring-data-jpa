package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
@RequiredArgsConstructor
public class AuthorDAOImpl implements AuthorDAO {

    private final EntityManagerFactory emF;

    private EntityManager getEntityManager() {
        return emF.createEntityManager();
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author getByNameAndSurname(String name, String surname) {
        TypedQuery<Author> query = getEntityManager().createQuery("select a from Author a " +
                "where a.firstName =: first_name and a.lastName =: last_name", Author.class);

        query.setParameter("first_name", name);
        query.setParameter("last_name", surname);

        return query.getSingleResult();
    }

    @Override
    public Author saveAuthor(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();

        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.clear();

        return entityManager.find(Author.class, author.getId());
    }

    @Override
    public void deleteAuthor(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Author author = entityManager.find(Author.class, id);
        entityManager.remove(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
