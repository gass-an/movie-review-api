package com.moviereview.api.exception.review;

import com.moviereview.api.exception.ApiException;
import org.springframework.http.HttpStatus;

/**
 * Indique qu'une note de review est en dehors de la plage autorisée.
 */
public class InvalidReviewRatingException extends ApiException {

    private static final String CODE = "INVALID_REVIEW_RATING";

    /**
     * Construit une exception décrivant la note invalide reçue.
     *
     * @param rating la note fournie.
     */
    public InvalidReviewRatingException(Integer rating) {
        super(CODE, "Rating must be between 1 and 5. Received=" + rating, HttpStatus.BAD_REQUEST);
    }
}

