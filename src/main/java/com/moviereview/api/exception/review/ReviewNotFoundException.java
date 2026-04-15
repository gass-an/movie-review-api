package com.moviereview.api.exception.review;

import com.moviereview.api.exception.ApiException;
import org.springframework.http.HttpStatus;

/**
 * Indique qu'une review demandée est introuvable.
 */
public class ReviewNotFoundException extends ApiException {

    private static final String CODE = "REVIEW_NOT_FOUND";

    /**
     * Construit une exception avec un message d'erreur spécifique.
     *
     * @param message le message d'erreur à exposer.
     */
    private ReviewNotFoundException(String message) {
        super(CODE, message, HttpStatus.NOT_FOUND);
    }

    /**
     * Construit une exception pour une review introuvable par identifiant.
     *
     * @param id l'identifiant recherché.
     * @return l'exception prête à être levée.
     */
    public static ReviewNotFoundException byId(Long id) {
        return new ReviewNotFoundException("Review not found with id=" + id);
    }

    /**
     * Construit une exception pour une review introuvable par film et utilisateur.
     *
     * @param movieId l'identifiant du film.
     * @param userId l'identifiant de l'utilisateur.
     * @return l'exception prête à être levée.
     */
    public static ReviewNotFoundException byMovieAndUser(Long movieId, Long userId) {
        return new ReviewNotFoundException(
                "Review not found for movieId=" + movieId + " and userId=" + userId
        );
    }
}

