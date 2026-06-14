package com.devsuperior.dsmovie.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.Movie;
import com.devsuperior.dsmovie.entities.Score;
import com.devsuperior.dsmovie.entities.User;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTest {

	@InjectMocks
	private ScoreService service;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ScoreRepository scoreRepository;

	private Long movieId;
	private String existingEmail;
	private String newEmail;
	private User existingUser;
	private User newUser;
	private Movie movie;
	private ScoreDTO existingUserScoreDTO;
	private ScoreDTO newUserScoreDTO;

	@BeforeEach
	void setUp() throws Exception {
		movieId = 1L;
		existingEmail = "existing@gmail.com";
		newEmail = "new@gmail.com";

		existingUser = new User(1L, existingEmail);
		newUser = new User(2L, newEmail);

		movie = new Movie(movieId, "Test Movie", 0.0, 0, "http://image.com");

		existingUserScoreDTO = new ScoreDTO();
		existingUserScoreDTO.setMovieId(movieId);
		existingUserScoreDTO.setEmail(existingEmail);
		existingUserScoreDTO.setScore(4.5);

		newUserScoreDTO = new ScoreDTO();
		newUserScoreDTO.setMovieId(movieId);
		newUserScoreDTO.setEmail(newEmail);
		newUserScoreDTO.setScore(5.0);

		Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
		Mockito.when(userRepository.findByEmail(existingEmail)).thenReturn(existingUser);
		Mockito.when(userRepository.findByEmail(newEmail)).thenReturn(null);

		// When saving a new user, return the saved user with ID
		Mockito.when(userRepository.saveAndFlush(Mockito.any(User.class))).thenAnswer(invocation -> {
			User userArg = invocation.getArgument(0);
			if (userArg.getEmail().equals(newEmail)) {
				return newUser;
			}
			return userArg;
		});

		// Mock scoreRepository saveAndFlush
		Mockito.when(scoreRepository.saveAndFlush(Mockito.any(Score.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Mock movieRepository save
		Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));
	}

	@Test
	public void saveScoreShouldReturnMovieDTOWhenUserExists() {
		// Mock the movie scores set to simulate a pre-existing score or the new score being added
		Score score = new Score();
		score.setMovie(movie);
		score.setUser(existingUser);
		score.setValue(4.5);
		movie.getScores().add(score);

		MovieDTO result = service.saveScore(existingUserScoreDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(movieId, result.getId());
		Assertions.assertEquals(4.5, result.getScore());
		Assertions.assertEquals(1, result.getCount());

		Mockito.verify(userRepository, Mockito.times(1)).findByEmail(existingEmail);
		Mockito.verify(userRepository, Mockito.never()).saveAndFlush(Mockito.any(User.class));
		Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);
		Mockito.verify(scoreRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Score.class));
		Mockito.verify(movieRepository, Mockito.times(1)).save(Mockito.any(Movie.class));
	}

	@Test
	public void saveScoreShouldCreateUserAndReturnMovieDTOWhenUserDoesNotExist() {
		Score score = new Score();
		score.setMovie(movie);
		score.setUser(newUser);
		score.setValue(5.0);
		movie.getScores().add(score);

		MovieDTO result = service.saveScore(newUserScoreDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(movieId, result.getId());
		Assertions.assertEquals(5.0, result.getScore());
		Assertions.assertEquals(1, result.getCount());

		Mockito.verify(userRepository, Mockito.times(1)).findByEmail(newEmail);
		Mockito.verify(userRepository, Mockito.times(1)).saveAndFlush(Mockito.any(User.class));
		Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);
		Mockito.verify(scoreRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Score.class));
		Mockito.verify(movieRepository, Mockito.times(1)).save(Mockito.any(Movie.class));
	}
}
