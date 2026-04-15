package com.moviereview.api.exception.movie;

import com.moviereview.api.exception.ApiException;
import org.springframework.http.HttpStatus;

/**
 * Indique qu'un film demandé est introuvable.
 */
public class MovieNotFoundException extends ApiException {

    private static final String CODE = "MOVIE_NOT_FOUND";

    /**
     * Crée une exception d'absence de film avec un message explicite.
     *
     * @param message le message d'erreur à exposer.
     */
    private MovieNotFoundException(String message) {
        super(CODE, message, HttpStatus.NOT_FOUND);
    }

    /**
     * Construit une exception pour un film introuvable par identifiant.
     *
     * @param id l'identifiant recherché.
     * @return l'exception prête à être levée.
     */
    public static MovieNotFoundException byId(Long id) {
        return new MovieNotFoundException("Movie not found with id=" + id);
    }
}

