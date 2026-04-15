package com.moviereview.api.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Représente les données nécessaires pour enregistrer un utilisateur.
 *
 * @param username le nom d'utilisateur.
 * @param email l'adresse email.
 */
@Schema(name = "UserRegisterRequest", description = "Payload d'enregistrement d'un utilisateur")
public record UserRegisterRequest(
        @Schema(description = "Nom d'utilisateur unique", example = "anthony")
        String username,
        @Schema(description = "Adresse email de l'utilisateur", example = "anthony@example.com")
        String email
) {
}

