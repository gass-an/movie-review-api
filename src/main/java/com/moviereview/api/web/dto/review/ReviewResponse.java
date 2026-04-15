package com.moviereview.api.web.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * Représente la réponse API d'une review.
 *
 * @param id l'identifiant de la review.
 * @param movieId l'identifiant du film concerné.
 * @param userId l'identifiant de l'utilisateur auteur.
 * @param rating la note attribuée.
 * @param comment le commentaire associé.
 * @param createdAt la date de création (lecture seule).
 * @param updatedAt la date de dernière mise à jour (lecture seule).
 */
@Schema(name = "ReviewResponse", description = "Représentation d'une review renvoyée par l'API")
public record ReviewResponse(
        @Schema(description = "Identifiant de la review", example = "10")
        Long id,
        @Schema(description = "Identifiant du film", example = "1")
        Long movieId,
        @Schema(description = "Identifiant de l'utilisateur", example = "2")
        Long userId,
        @Schema(description = "Note attribuée", example = "5")
        Integer rating,
        @Schema(description = "Commentaire de la review", example = "Excellent film, très immersif")
        String comment,
        @Schema(description = "Date de création", example = "2026-04-15T17:35:00", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,
        @Schema(description = "Date de dernière mise à jour", example = "2026-04-15T18:12:00", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) {
}

