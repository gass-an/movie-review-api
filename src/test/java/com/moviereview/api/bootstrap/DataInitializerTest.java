package com.moviereview.api.bootstrap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moviereview.api.entity.Movie;
import com.moviereview.api.entity.Review;
import com.moviereview.api.entity.User;
import com.moviereview.api.repository.MovieRepository;
import com.moviereview.api.repository.ReviewRepository;
import com.moviereview.api.repository.UserRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ApplicationArguments applicationArguments;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void run_shouldNotDuplicateData_whenSeedAlreadyExists() throws Exception {
        User leonardo = User.builder().id(1L).username("Leonardo").email("leonardo@ninjaturtle.com").build();
        User donatello = User.builder().id(2L).username("Donatello").email("donatello@ninjaturtle.com").build();
        User raphael = User.builder().id(3L).username("Raphael").email("raphael@ninjaturtle.com").build();
        User michelangelo = User.builder().id(4L).username("Michelangelo").email("michelangelo@ninjaturtle.com").build();
        User splinter = User.builder().id(5L).username("Splinter").email("splinter@ninjaturtle.com").build();

        Movie teenageMutantNinjaTurtles = Movie.builder().id(10L).build();
        Movie leSeigneurDesAnneaux = Movie.builder().id(11L).build();
        Movie harryPotter = Movie.builder().id(12L).build();
        Movie matrix = Movie.builder().id(13L).build();

        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(leonardo))
                .thenReturn(Optional.of(donatello))
                .thenReturn(Optional.of(raphael))
                .thenReturn(Optional.of(michelangelo))
                .thenReturn(Optional.of(splinter));

        when(movieRepository.findByTitleAndReleaseDate(anyString(), any(LocalDate.class)))
                .thenReturn(Optional.of(teenageMutantNinjaTurtles))
                .thenReturn(Optional.of(leSeigneurDesAnneaux))
                .thenReturn(Optional.of(harryPotter))
                .thenReturn(Optional.of(matrix));

        when(reviewRepository.findByMovieIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(Review.builder().id(100L).build()));

        dataInitializer.run(applicationArguments);

        verify(userRepository, times(5)).findByUsername(anyString());
        verify(movieRepository, times(4)).findByTitleAndReleaseDate(anyString(), any(LocalDate.class));
        verify(reviewRepository, times(18)).findByMovieIdAndUserId(anyLong(), anyLong());

        verify(userRepository, never()).save(any(User.class));
        verify(movieRepository, never()).save(any(Movie.class));
        verify(reviewRepository, never()).save(any(Review.class));
    }
}

