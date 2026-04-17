package com.moviereview.api.exception.user;

import com.moviereview.api.exception.ApiException;
import org.springframework.http.HttpStatus;

/**
 * Indique qu'un utilisateur existe déjà avec le même username ou email.
 */
public class UserAlreadyExistsException extends ApiException {

    private static final String CODE = "USER_ALREADY_EXISTS";

    /**
     * Construit une exception avec un message d'erreur spécifique.
     *
     * @param message le message d'erreur à exposer.
     */
    private UserAlreadyExistsException(String message) {
        super(CODE, message, HttpStatus.CONFLICT);
    }

    /**
     * Construit une exception pour un username déjà utilisé.
     *
     * @param username le username en conflit.
     * @return l'exception prête à être levée.
     */
    public static UserAlreadyExistsException byUsername(String username) {
        return new UserAlreadyExistsException("User already exists with username=" + username);
    }

    /**
     * Construit une exception pour un email déjà utilisé.
     *
     * @param email l'email en conflit.
     * @return l'exception prête à être levée.
     */
    public static UserAlreadyExistsException byEmail(String email) {
        return new UserAlreadyExistsException("User already exists with email=" + email);
    }
}

