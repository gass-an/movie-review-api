package com.moviereview.api.exception;

/**
 * Représente le corps standard renvoyé en cas d'erreur API.
 *
 * @param code le code applicatif de l'erreur.
 * @param message le message lisible par un humain.
 * @param status le statut HTTP numérique.
 */
public record ErrorResponse(
        String code,
        String message,
        int status
) {
}


