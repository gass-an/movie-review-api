package com.moviereview.api.dto.review;

/**
 * Représente les champs modifiables d'une review.
 *
 * @param rating la nouvelle note, si elle doit être modifiée.
 * @param comment le nouveau commentaire, si nécessaire.
 */
public record UpdateReviewRequest(
        Integer rating,
        String comment
) {
}

