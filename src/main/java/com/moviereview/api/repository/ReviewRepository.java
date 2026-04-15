package com.moviereview.api.repository;

import com.moviereview.api.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Accès aux données des reviews.
 */
public interface ReviewRepository extends JpaRepository <Review, Long> {

    /**
     * Récupère toutes les reviews liées à un film donné.
     *
     * @param movieId l'identifiant du film.
     * @return la liste des reviews associées au film.
     */
    List<Review> findByMovieId(Long movieId);

    /**
     * Récupère toutes les reviews écrites par un utilisateur donné.
     *
     * @param userId l'identifiant de l'utilisateur.
     * @return la liste des reviews associées à l'utilisateur.
     */
    List<Review> findByUserId(Long userId);

    /**
     * Récupère la review correspondant à un couple film/utilisateur.
     *
     * @param movieId l'identifiant du film.
     * @param userId l'identifiant de l'utilisateur.
     * @return la review correspondante si elle existe.
     */
    Optional<Review> findByMovieIdAndUserId(Long movieId, Long userId);
}
