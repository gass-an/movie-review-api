package com.moviereview.api.repository;

import com.moviereview.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Accès aux données des utilisateurs.
 */
public interface UserRepository extends JpaRepository <User, Long> {

    /**
     * Vérifie l'existence d'un utilisateur avec le même nom d'utilisateur.
     *
     * @param username le nom d'utilisateur à vérifier.
     * @return true si un utilisateur existe déjà avec ce nom.
     */
    boolean existsByUsername(String username);

    /**
     * Vérifie l'existence d'un utilisateur avec la même adresse email.
     *
     * @param email l'email à vérifier.
     * @return true si un utilisateur existe déjà avec cet email.
     */
    boolean existsByEmail(String email);

    /**
     * Vérifie l'existence d'un autre utilisateur avec le même nom d'utilisateur.
     *
     * @param username le nom d'utilisateur à vérifier.
     * @param id l'identifiant à exclure.
     * @return true si un autre utilisateur porte déjà ce nom.
     */
    boolean existsByUsernameAndIdNot(String username, Long id);

    /**
     * Vérifie l'existence d'un autre utilisateur avec la même adresse email.
     *
     * @param email l'email à vérifier.
     * @param id l'identifiant à exclure.
     * @return true si un autre utilisateur porte déjà cet email.
     */
    boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * Récupère un utilisateur à partir de son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur recherché.
     * @return l'utilisateur correspondant si trouvé.
     */
    Optional<User> findByUsername(String username);
}
