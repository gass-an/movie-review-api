package com.moviereview.api.dto.user;

/**
 * Représente les données nécessaires pour enregistrer un utilisateur.
 *
 * @param username le nom d'utilisateur.
 * @param email l'adresse email.
 */
public record UserRegisterRequest(
        String username,
        String email
) {
}

