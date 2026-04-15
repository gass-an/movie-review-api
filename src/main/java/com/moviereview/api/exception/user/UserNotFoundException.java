package com.moviereview.api.exception.user;

import com.moviereview.api.exception.ApiException;
import org.springframework.http.HttpStatus;

/**
 * Indique qu'un utilisateur demandé est introuvable.
 */
public class UserNotFoundException extends ApiException {

    private static final String CODE = "USER_NOT_FOUND";

    /**
     * Construit une exception avec un message d'erreur spécifique.
     *
     * @param message le message d'erreur à exposer.
     */
    private UserNotFoundException(String message) {
        super(CODE, message, HttpStatus.NOT_FOUND);
    }

    /**
     * Construit une exception pour un utilisateur introuvable par identifiant.
     *
     * @param id l'identifiant recherché.
     * @return l'exception prête à être levée.
     */
    public static UserNotFoundException byId(Long id) {
        return new UserNotFoundException("User not found with id=" + id);
    }

    /**
     * Construit une exception pour un utilisateur introuvable par nom d'utilisateur.
     *
     * @param username le nom d'utilisateur recherché.
     * @return l'exception prête à être levée.
     */
    public static UserNotFoundException byUsername(String username) {
        return new UserNotFoundException("User not found with username=" + username);
    }
}


