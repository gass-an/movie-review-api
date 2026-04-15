package com.moviereview.api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * Représente les champs modifiables d'un film.
 *
 * @param title le nouveau titre, si fourni.
 * @param description la nouvelle description, si fournie.
 * @param releaseDate la nouvelle date de sortie, si fournie.
 */
@Schema(name = "MoviePatchRequest", description = "Payload de mise à jour partielle d'un film")
public record MoviePatchRequest(
        @Schema(description = "Nouveau titre du film", example = "Inception (Director's Cut)")
        String title,
        @Schema(description = "Nouvelle description du film", example = "Version étendue du film")
        String description,
        @Schema(description = "Nouvelle date de sortie", example = "2010-07-16")
        LocalDate releaseDate
) {
}

