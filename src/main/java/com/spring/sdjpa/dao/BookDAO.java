package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Book;

public interface BookDAO {
    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
