package com.moviereview.api.repository;

import com.moviereview.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Accès aux données des utilisateurs.
 */
public interface UserRepository extends JpaRepository <User, Long> {

    /**
     * Récupère un utilisateur à partir de son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur recherché.
     * @return l'utilisateur correspondant si trouvé.
     */
    Optional<User> findByUsername(String username);
}
