package com.moviereview.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Convertit les exceptions de l'application en réponses HTTP standardisées.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Transforme une exception métier en réponse d'erreur structurée.
     *
     * @param ex l'exception métier interceptée.
     * @return la réponse HTTP correspondante.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorResponse body = new ErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                ex.getStatus().value()
        );
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    /**
     * Convertit les violations d'intégrité (ex: contraintes d'unicité) en conflit HTTP générique.
     *
     * @param ex l'exception de persistance interceptée.
     * @return une réponse HTTP 409 avec un corps d'erreur standard.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorResponse body = new ErrorResponse(
                "DATA_INTEGRITY_VIOLATION",
                "Data integrity violation",
                HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Transforme toute exception inattendue en erreur interne générique.
     *
     * @param ex l'exception non gérée.
     * @return une réponse HTTP 500 avec un corps d'erreur standard.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex) {
        ErrorResponse body = new ErrorResponse(
                "INTERNAL_ERROR",
                "Unexpected server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}


