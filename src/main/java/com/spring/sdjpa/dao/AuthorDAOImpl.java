package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;
import com.spring.sdjpa.repo.AuthorRepo;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorDAOImpl implements AuthorDAO {

    private final EntityManagerFactory emF;
    private final AuthorRepo authorRepo;

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
        return authorRepo.getById(id);
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
        return authorRepo.findAuthorByFirstNameAndLastName(name, surname)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        Author authorById = authorRepo.getById(author.getId());
        authorById.setLastName(author.getLastName());
        authorById.setFirstName(author.getFirstName());

        return authorRepo.save(authorById);

    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepo.deleteById(id);
    }
}
