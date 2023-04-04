package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;
import com.spring.sdjpa.domain.Book;

import java.util.List;

public interface BookDAO {
    List<Book> findAll();
    Book getByISBN(String isbn);
    Book getById(Long id);
    Book findBookByTitle(String title);
    Book saveNewBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(Long id);
}
