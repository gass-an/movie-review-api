package com.moviereview.api.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Représente les données nécessaires pour créer une review.
 *
 * @param movieId l'identifiant du film évalué.
 * @param userId l'identifiant de l'utilisateur auteur de la review.
 * @param rating la note attribuée, comprise entre 1 et 5.
 * @param comment le commentaire associé à la review.
 */
@Schema(name = "CreateReviewRequest", description = "Payload de création d'une review")
public record CreateReviewRequest(
        @Schema(description = "Identifiant du film", example = "1")
        Long movieId,
        @Schema(description = "Identifiant de l'utilisateur", example = "2")
        Long userId,
        @Schema(description = "Note entre 1 et 5", example = "5")
        Integer rating,
        @Schema(description = "Commentaire de la review", example = "Excellent film, très immersif")
        String comment
) {
}

