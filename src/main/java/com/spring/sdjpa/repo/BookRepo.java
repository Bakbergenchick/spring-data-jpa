package com.spring.sdjpa.repo;

import com.spring.sdjpa.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;


@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Book jpaNamed(@Param("title") String title);
    @Query(value = "select * from Book where title = :title", nativeQuery = true)
    Book findBookByTitleWithQueryNative(@Param("title") String title);
    @Query("select b from Book b where b.title = :title")
    Book findBookByTitleWithQueryNamed(@Param("title") String title);
    @Query("select b from Book b where b.title = ?1")
    Book findBookByTitleWithQuery(String title);
    Optional<Book> findBookByTitle(String title);
    Book readByTitle(String title);
    @Nullable
    Book getByTitle(@Nullable String title);
    @Async
    Future<Book> queryByTitle(String title);
}
