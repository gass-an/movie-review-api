package com.moviereview.api.exception.movie;

import com.moviereview.api.exception.ApiException;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;

/**
 * Indique qu'un film existe deja avec le meme titre et la meme date de sortie.
 */
public class MovieAlreadyExistsException extends ApiException {

    private static final String CODE = "MOVIE_ALREADY_EXISTS";

    /**
     * Construit une exception avec un message d'erreur specifique.
     *
     * @param message le message d'erreur a exposer.
     */
    private MovieAlreadyExistsException(String message) {
        super(CODE, message, HttpStatus.CONFLICT);
    }

    /**
     * Construit une exception pour un doublon de film sur titre/date de sortie.
     *
     * @param title le titre en conflit.
     * @param releaseDate la date de sortie en conflit.
     * @return l'exception prete a etre levee.
     */
    public static MovieAlreadyExistsException byTitleAndReleaseDate(String title, LocalDate releaseDate) {
        return new MovieAlreadyExistsException(
                "Movie already exists with title=" + title + " and releaseDate=" + releaseDate
        );
    }
}

