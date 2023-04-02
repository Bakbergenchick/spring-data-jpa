package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;

public interface AuthorDAO {
    Author getById(Long id);

    Author getByNameAndSurname(String name, String surname);

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthor(Long id);
}
