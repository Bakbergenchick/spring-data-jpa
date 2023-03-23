package com.spring.sdjpa;

import com.spring.sdjpa.repo.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SdJpaApplicationTests {

	@Autowired
	private BookRepo bookRepo;

	@Test
	void countRows(){
		long count = bookRepo.count();

		assertThat(count).isGreaterThan(0);
	}

	@Test
	void contextLoads() {
	}

}
