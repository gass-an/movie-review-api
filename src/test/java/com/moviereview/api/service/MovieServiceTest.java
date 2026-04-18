package com.moviereview.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moviereview.api.entity.Movie;
import com.moviereview.api.exception.movie.MovieAlreadyExistsException;
import com.moviereview.api.repository.MovieRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void create_shouldThrowBusinessException_whenMovieAlreadyExists() {
        LocalDate releaseDate = LocalDate.of(2010, 7, 16);
        Movie movie = Movie.builder().title("Inception").releaseDate(releaseDate).build();

        when(movieRepository.existsByTitleAndReleaseDate("Inception", releaseDate)).thenReturn(true);

        MovieAlreadyExistsException ex = assertThrows(MovieAlreadyExistsException.class, () -> movieService.create(movie));

        assertEquals("MOVIE_ALREADY_EXISTS", ex.getCode());
        verify(movieRepository, never()).save(movie);
    }

    @Test
    void update_shouldThrowBusinessException_whenAnotherMovieHasSameTitleAndDate() {
        Long id = 1L;
        LocalDate releaseDate = LocalDate.of(2010, 7, 16);
        Movie existing = Movie.builder().id(id).title("Old title").releaseDate(releaseDate).build();

        when(movieRepository.findById(id)).thenReturn(Optional.of(existing));
        when(movieRepository.existsByTitleAndReleaseDateAndIdNot("Inception", releaseDate, id)).thenReturn(true);

        MovieAlreadyExistsException ex = assertThrows(
                MovieAlreadyExistsException.class,
                () -> movieService.updateTitleAndDescriptionAndReleaseDate(id, "Inception", null, null)
        );

        assertEquals("MOVIE_ALREADY_EXISTS", ex.getCode());
        verify(movieRepository, never()).save(existing);
    }
}

