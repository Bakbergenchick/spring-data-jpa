package com.spring.sdjpa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

//@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "find_all_books", query = "select b from Book b"),
        @NamedQuery(name = "find_by_title", query = "select b from Book b where b.title = :title"),
        @NamedQuery(name = "Book.jpaNamed", query = "select b from Book b where b.title = :title")
})
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
    private Long authorId;

    public Book(String title, String isbn, String publisher, Long authorId) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.authorId = authorId;
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
