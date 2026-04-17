package com.moviereview.api.exception;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Représente le corps standard renvoyé en cas d'erreur API.
 *
 * @param code le code applicatif de l'erreur.
 * @param message le message lisible par un humain.
 * @param status le statut HTTP numérique.
 */
public record ErrorResponse(

        @Schema(description = "Code d'erreur spécifique à l'application", example = "TEAPOT_ERROR")
        String code,
        @Schema(description = "Message d'erreur lisible par un humain", example = "I'm a teapot")
        String message,
        @Schema(description = "Statut HTTP de l'erreur", example = "418")
        int status
) {
}
