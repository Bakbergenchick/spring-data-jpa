package com.spring.sdjpa.repo;

import com.spring.sdjpa.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
}
