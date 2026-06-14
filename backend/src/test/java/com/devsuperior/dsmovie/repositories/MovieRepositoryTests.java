package com.devsuperior.dsmovie.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devsuperior.dsmovie.entities.Movie;

@DataJpaTest
public class MovieRepositoryTests {

	@Autowired
	private MovieRepository repository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalMovies;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 999L;
		countTotalMovies = 29L; // In import.sql there are 29 movies inserted
	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
		Optional<Movie> result = repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals("The Witcher", result.get().getTitle());
	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
		Optional<Movie> result = repository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void saveShouldPersistMovieWithAutoIncrementWhenIdIsNull() {
		Movie movie = new Movie(null, "Test Movie", 0.0, 0, "http://image.com");
		movie = repository.save(movie);

		Assertions.assertNotNull(movie.getId());
		Assertions.assertEquals(countTotalMovies + 1, movie.getId());
		Assertions.assertEquals("Test Movie", movie.getTitle());
	}
}
