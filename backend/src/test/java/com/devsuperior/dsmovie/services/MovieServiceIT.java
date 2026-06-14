package com.devsuperior.dsmovie.services;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmovie.dto.MovieDTO;

@SpringBootTest
@Transactional
public class MovieServiceIT {

	@Autowired
	private MovieService service;

	private Long existingId;
	private Long nonExistingId;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 999L;
	}

	@Test
	public void findAllShouldReturnPageOfMovieDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<MovieDTO> result = service.findAll(pageable);

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(10, result.getContent().size()); // 10 elements on first page
		Assertions.assertEquals("The Witcher", result.getContent().get(0).getTitle());
	}

	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
		MovieDTO result = service.findById(existingId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingId, result.getId());
		Assertions.assertEquals("The Witcher", result.getTitle());
	}

	@Test
	public void findByIdShouldThrowNoSuchElementExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			service.findById(nonExistingId);
		});
	}
}
