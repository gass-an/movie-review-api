package com.moviereview.api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * Représente la réponse API d'un utilisateur.
 *
 * @param id l'identifiant de l'utilisateur.
 * @param username le nom d'utilisateur.
 * @param email l'adresse email.
 * @param createdAt la date de création (lecture seule).
 */
@Schema(name = "UserResponse", description = "Représentation d'un utilisateur renvoyée par l'API")
public record UserResponse(
        @Schema(description = "Identifiant de l'utilisateur", example = "2")
        Long id,
        @Schema(description = "Nom d'utilisateur", example = "anthony")
        String username,
        @Schema(description = "Adresse email", example = "anthony@example.com")
        String email,
        @Schema(description = "Date de création", example = "2026-04-15T16:55:00", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt
) {
}

