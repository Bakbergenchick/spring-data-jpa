package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;

import java.util.List;

public interface AuthorDAO {
    List<Author> findAll();
    List<Author> listAuthorByLastNameLike(String lastName);
    Author getById(Long id);
    Author findAuthorByNameNative(String firstName, String lastName);
    Author getByNameAndSurname(String name, String surname);
    Author saveAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthor(Long id);
}
