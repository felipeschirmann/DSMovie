package com.devsuperior.dsmovie.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;

@SpringBootTest
@Transactional
public class ScoreServiceIT {

	@Autowired
	private ScoreService service;

	private Long movieId;
	private String existingEmail;
	private String newEmail;
	private ScoreDTO existingUserScoreDTO;
	private ScoreDTO newUserScoreDTO;

	@BeforeEach
	void setUp() throws Exception {
		movieId = 1L; // The Witcher (existing movie in import.sql, score avg = 4.5, count = 2)
		existingEmail = "maria@gmail.com";
		newEmail = "newuser@gmail.com";

		existingUserScoreDTO = new ScoreDTO();
		existingUserScoreDTO.setMovieId(movieId);
		existingUserScoreDTO.setEmail(existingEmail);
		existingUserScoreDTO.setScore(5.0);

		newUserScoreDTO = new ScoreDTO();
		newUserScoreDTO.setMovieId(movieId);
		newUserScoreDTO.setEmail(newEmail);
		newUserScoreDTO.setScore(3.0);
	}

	@Test
	public void saveScoreShouldReturnMovieDTOWithUpdatedAverageForExistingUser() {
		MovieDTO result = service.saveScore(existingUserScoreDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(movieId, result.getId());
		Assertions.assertEquals(4.5, result.getScore());
		Assertions.assertEquals(2, result.getCount());
	}

	@Test
	public void saveScoreShouldCreateUserAndReturnMovieDTOWithUpdatedAverageForNewUser() {
		MovieDTO result = service.saveScore(newUserScoreDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(movieId, result.getId());
		Assertions.assertEquals(4.0, result.getScore());
		Assertions.assertEquals(3, result.getCount());
	}
}
