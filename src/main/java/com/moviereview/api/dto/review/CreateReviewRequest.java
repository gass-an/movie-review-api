package com.moviereview.api.dto.review;

/**
 * Représente les données nécessaires pour créer une review.
 *
 * @param movieId l'identifiant du film évalué.
 * @param userId l'identifiant de l'utilisateur auteur de la review.
 * @param rating la note attribuée, comprise entre 1 et 5.
 * @param comment le commentaire associé à la review.
 */
public record CreateReviewRequest(
        Long movieId,
        Long userId,
        Integer rating,
        String comment
) {
}

