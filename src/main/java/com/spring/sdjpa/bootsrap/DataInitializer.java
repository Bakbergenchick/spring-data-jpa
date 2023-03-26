package com.spring.sdjpa.bootsrap;

import com.spring.sdjpa.domain.Book;
import com.spring.sdjpa.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepo bookRepo;

    @Override
    public void run(String... args) throws Exception {
        bookRepo.deleteAll();

        Book book1 = new Book("Ddd1", "123", "AlmatyKitap", null);
        Book book2 = new Book("Ddd2", "5675", "Atamura", null);

        bookRepo.save(book1);
        bookRepo.save(book2);

        bookRepo.findAll().forEach(book -> {
            System.out.println("Id: " + book.getId());
            System.out.println("Title: " + book.getTitle());
        });
    }
}
