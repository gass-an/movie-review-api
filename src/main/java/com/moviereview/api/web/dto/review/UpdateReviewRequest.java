package com.moviereview.api.web.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Représente les champs modifiables d'une review.
 *
 * @param rating la nouvelle note, si elle doit être modifiée.
 * @param comment le nouveau commentaire, si nécessaire.
 */
@Schema(name = "UpdateReviewRequest", description = "Payload de mise à jour partielle d'une review")
public record UpdateReviewRequest(
        @Schema(description = "Nouvelle note entre 1 et 5", example = "4")
        Integer rating,
        @Schema(description = "Nouveau commentaire", example = "Après revision, toujours très bon")
        String comment
) {
}

