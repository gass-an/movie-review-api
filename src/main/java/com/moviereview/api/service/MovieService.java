package com.moviereview.api.service;

import com.moviereview.api.entity.Movie;
import com.moviereview.api.exception.movie.MovieNotFoundException;
import com.moviereview.api.repository.MovieRepository;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Contient la logique métier liée aux films.
 */
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * Enregistre un nouveau film.
     *
     * @param movie le film à persister.
     * @return le film enregistré.
     */
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * Récupère tous les films.
     *
     * @return la liste de tous les films.
     */
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    /**
     * Recherche les films dont le titre contient une chaîne donnée.
     *
     * @param title le fragment de titre recherché.
     * @return la liste des films correspondants.
     */
    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Récupère un film par son identifiant.
     *
     * @param id l'identifiant du film.
     * @return le film trouvé.
     */
    public Movie getById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.byId(id));
    }

    /**
     * Met à jour les champs modifiables d'un film.
     *
     * @param id l'identifiant du film à mettre à jour.
     * @param title le nouveau titre, si présent.
     * @param description la nouvelle description, si présente.
     * @param releaseDate la nouvelle date de sortie, si présente.
     * @return le film mis à jour.
     */
    public Movie updateTitleAndDescriptionAndReleaseDate(Long id, String title, String description, LocalDate releaseDate) {
        Movie movie = getById(id);

        if (title != null) {
            movie.setTitle(title);
        }
        if (description != null) {
            movie.setDescription(description);
        }
        if (releaseDate != null) {
            movie.setReleaseDate(releaseDate);
        }
        return movieRepository.save(movie);
    }

    /**
     * Supprime un film par son identifiant.
     *
     * @param id l'identifiant du film à supprimer.
     */
    public void deleteById(Long id) {
        Movie movie = getById(id);
        movieRepository.delete(movie);
    }
}

