package com.spring.sdjpa.bootsrap;

import com.spring.sdjpa.domain.AuthorUuid;
import com.spring.sdjpa.domain.Book;
import com.spring.sdjpa.domain.BookUuid;
import com.spring.sdjpa.repo.AuthorUuidRepository;
import com.spring.sdjpa.repo.BookRepo;
import com.spring.sdjpa.repo.BookUuidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepo bookRepo;
    private final AuthorUuidRepository authorUuidRepo;
    private final BookUuidRepository bookUuidRepo;

    @Override
    public void run(String... args) throws Exception {
        bookRepo.deleteAll();
        authorUuidRepo.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);
        Book savedDDD = bookRepo.save(bookDDD);

        Book bookSIA = new Book("Spring In Action", "234234", "Oriely", null);
        Book savedSIA = bookRepo.save(bookSIA);

        bookRepo.findAll().forEach(book -> {
            System.out.println("Book Id: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("Joe");
        authorUuid.setLastName("Buck");
        AuthorUuid savedAuthor = authorUuidRepo.save(authorUuid);
        System.out.println("Saved Author UUID: " + savedAuthor.getId() );

        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("All About UUIDs");
        BookUuid savedBookUuid = bookUuidRepo.save(bookUuid);
        System.out.println("Saved Book UUID: " + savedBookUuid.getId());
    }
}
