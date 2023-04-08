package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;
import com.spring.sdjpa.domain.Book;
import com.spring.sdjpa.repo.BookRepo;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDAOImpl implements BookDAO {
    private final BookRepo bookRepo;

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        Page<Book> books = bookRepo.findAll(pageable);
        return books.getContent();
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return bookRepo.findAll(pageable).getContent();
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        Pageable pageable  = PageRequest.ofSize(pageSize);

        if (offset > 0){
            pageable = pageable.withPage(offset / pageSize);
        } else{
            pageable = pageable.withPage(0);
        }

        return this.findAllBooks(pageable);
    }

    @Override
    public List<Book> findAll() {
        return bookRepo.findAll();
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

}
