package com.moviereview.api.dto.movie;

import java.time.LocalDate;

/**
 * Représente les champs modifiables d'un film.
 *
 * @param title le nouveau titre, si fourni.
 * @param description la nouvelle description, si fournie.
 * @param releaseDate la nouvelle date de sortie, si fournie.
 */
public record MoviePatchRequest(
        String title,
        String description,
        LocalDate releaseDate
) {
}

