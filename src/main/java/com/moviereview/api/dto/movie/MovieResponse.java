package com.moviereview.api.dto.movie;

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
public record MovieResponse(
        Long id,
        String title,
        String description,
        LocalDate releaseDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

