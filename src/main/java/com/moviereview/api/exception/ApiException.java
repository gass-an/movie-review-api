package com.moviereview.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception métier de base contenant un code applicatif et un statut HTTP.
 */
@Getter
public class ApiException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

    /**
     * Construit une exception métier typée.
     *
     * @param code le code applicatif de l'erreur.
     * @param message le message d'erreur exposé.
     * @param status le statut HTTP à renvoyer.
     */
    public ApiException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

}


