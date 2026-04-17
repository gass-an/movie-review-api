package com.moviereview.api.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Représente les données modifiables d'un utilisateur.
 *
 * @param username le nouveau nom d'utilisateur, si présent.
 * @param email la nouvelle adresse email, si présente.
 */
@Schema(name = "UserUpdateRequest", description = "Payload de mise à jour partielle d'un utilisateur")
public record UserUpdateRequest(
        @Schema(description = "Nouveau nom d'utilisateur", example = "JoeDalton2")
        String username,
        @Schema(description = "Nouvelle adresse email", example = "joe.dalton2@example.com")
        String email
) {
}

