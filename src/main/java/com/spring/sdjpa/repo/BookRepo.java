package com.spring.sdjpa.repo;

import com.spring.sdjpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {

}
