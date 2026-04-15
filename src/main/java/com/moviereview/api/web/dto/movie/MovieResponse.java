package com.moviereview.api.web.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Représente la réponse API d'un film.
 *
 * @param id l'identifiant du film.
 * @param title le titre du film.
 * @param description la description du film.
 * @param releaseDate la date de sortie du film.
 * @param createdAt la date de création (lecture seule).
 * @param updatedAt la date de dernière mise à jour (lecture seule).
 */
@Schema(name = "MovieResponse", description = "Représentation d'un film renvoyée par l'API")
public record MovieResponse(
        @Schema(description = "Identifiant du film", example = "1")
        Long id,
        @Schema(description = "Titre du film", example = "Inception")
        String title,
        @Schema(description = "Description du film", example = "Un thriller de science-fiction sur les rêves")
        String description,
        @Schema(description = "Date de sortie du film", example = "2010-07-16")
        LocalDate releaseDate,
        @Schema(description = "Date de création", example = "2026-04-15T17:20:00", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,
        @Schema(description = "Date de dernière mise à jour", example = "2026-04-15T18:10:00", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) {
}

