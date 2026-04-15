package com.moviereview.api.dto.review;

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
public record ReviewResponse(
        Long id,
        Long movieId,
        Long userId,
        Integer rating,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

