package com.moviereview.api.exception.review;

import com.moviereview.api.exception.ApiException;
import org.springframework.http.HttpStatus;

/**
 * Indique qu'une review existe déjà pour le couple film/utilisateur.
 */
public class ReviewAlreadyExistsException extends ApiException {

    private static final String CODE = "REVIEW_ALREADY_EXISTS";

    /**
     * Construit une exception avec un message d'erreur spécifique.
     *
     * @param message le message d'erreur à exposer.
     */
    private ReviewAlreadyExistsException(String message) {
        super(CODE, message, HttpStatus.CONFLICT);
    }

    /**
     * Construit une exception pour un doublon de review sur un film et un utilisateur.
     *
     * @param movieId l'identifiant du film.
     * @param userId l'identifiant de l'utilisateur.
     * @return l'exception prête à être levée.
     */
    public static ReviewAlreadyExistsException byMovieAndUser(Long movieId, Long userId) {
        return new ReviewAlreadyExistsException(
                "Review already exists for movieId=" + movieId + " and userId=" + userId
        );
    }
}

