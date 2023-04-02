package com.spring.sdjpa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

//@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;
    private String publisher;
    @Transient
    private Author author;

    public Book(String title, String isbn, String publisher, Author authorId) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.author = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
