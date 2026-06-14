package com.devsuperior.dsmovie.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devsuperior.dsmovie.entities.User;

@DataJpaTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository repository;

	private String existingEmail;
	private String nonExistingEmail;

	@BeforeEach
	void setUp() throws Exception {
		existingEmail = "maria@gmail.com"; // In import.sql there is a user with email maria@gmail.com
		nonExistingEmail = "doesnotexist@gmail.com";
	}

	@Test
	public void findByEmailShouldReturnUserWhenEmailExists() {
		User result = repository.findByEmail(existingEmail);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingEmail, result.getEmail());
		Assertions.assertEquals(1L, result.getId());
	}

	@Test
	public void findByEmailShouldReturnNullWhenEmailDoesNotExist() {
		User result = repository.findByEmail(nonExistingEmail);
		Assertions.assertNull(result);
	}
}
