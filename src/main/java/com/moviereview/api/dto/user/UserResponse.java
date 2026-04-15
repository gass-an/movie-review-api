package com.moviereview.api.dto.user;

import java.time.LocalDateTime;

/**
 * Représente la réponse API d'un utilisateur.
 *
 * @param id l'identifiant de l'utilisateur.
 * @param username le nom d'utilisateur.
 * @param email l'adresse email.
 * @param createdAt la date de création (lecture seule).
 */
public record UserResponse(
        Long id,
        String username,
        String email,
        LocalDateTime createdAt
) {
}

