package com.moviereview.api.repository;

import com.moviereview.api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Accès aux données des films.
 */
public interface MovieRepository extends JpaRepository <Movie, Long> {

    /**
     * Recherche les films dont le titre contient une chaîne donnée, sans tenir compte de la casse.
     *
     * @param title le fragment de titre recherché.
     * @return la liste des films correspondants.
     */
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
