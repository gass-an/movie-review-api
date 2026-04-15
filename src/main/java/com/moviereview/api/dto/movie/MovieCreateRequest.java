package com.moviereview.api.dto.movie;

import java.time.LocalDate;

/**
 * Représente les données nécessaires pour créer un film.
 *
 * @param title le titre du film.
 * @param description la description du film.
 * @param releaseDate la date de sortie du film.
 */
public record MovieCreateRequest(
        String title,
        String description,
        LocalDate releaseDate
) {
}

