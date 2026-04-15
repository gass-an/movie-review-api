package com.moviereview.api.web.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * Représente les données nécessaires pour créer un film.
 *
 * @param title le titre du film.
 * @param description la description du film.
 * @param releaseDate la date de sortie du film.
 */
@Schema(name = "MovieCreateRequest", description = "Payload de création d'un film")
public record MovieCreateRequest(
        @Schema(description = "Titre du film", example = "Inception")
        String title,
        @Schema(description = "Description du film", example = "Un thriller de science-fiction sur les rêves")
        String description,
        @Schema(description = "Date de sortie du film", example = "2010-07-16")
        LocalDate releaseDate
) {
}

