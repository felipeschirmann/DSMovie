package com.devsuperior.dsmovie.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MovieControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void findAllShouldReturnPageOfMovieDTO() throws Exception {
		mockMvc.perform(get("/movies")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").exists())
				.andExpect(jsonPath("$.content").isArray());
	}

	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() throws Exception {
		mockMvc.perform(get("/movies/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.title").value("The Witcher"));
	}

	@Test
	public void findByIdShouldThrowExceptionWhenIdDoesNotExist() throws Exception {
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
			mockMvc.perform(get("/movies/999")
					.accept(MediaType.APPLICATION_JSON));
		});
	}
}
