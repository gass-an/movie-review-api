package com.moviereview.api.repository;

import com.moviereview.api.entity.Movie;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

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

    /**
     * Recherche un film par son titre exact et sa date de sortie.
     *
     * @param title le titre exact du film.
     * @param releaseDate la date de sortie du film.
     * @return le film correspondant s'il existe.
     */
    Optional<Movie> findByTitleAndReleaseDate(String title, LocalDate releaseDate);

    /**
     * Verifie l'existence d'un film avec le meme titre et la meme date de sortie.
     *
     * @param title le titre a verifier.
     * @param releaseDate la date de sortie a verifier.
     * @return true si un film existe deja avec ce couple.
     */
    boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);

    /**
     * Verifie l'existence d'un autre film avec le meme titre et la meme date de sortie.
     *
     * @param title le titre a verifier.
     * @param releaseDate la date de sortie a verifier.
     * @param id l'identifiant a exclure.
     * @return true si un autre film existe deja avec ce couple.
     */
    boolean existsByTitleAndReleaseDateAndIdNot(String title, LocalDate releaseDate, Long id);
}
