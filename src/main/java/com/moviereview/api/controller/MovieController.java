package com.moviereview.api.controller;

import com.moviereview.api.dto.movie.MovieCreateRequest;
import com.moviereview.api.dto.movie.MoviePatchRequest;
import com.moviereview.api.dto.movie.MovieResponse;
import com.moviereview.api.dto.review.ReviewResponse;
import com.moviereview.api.controller.mapper.MovieMapper;
import com.moviereview.api.controller.mapper.ReviewMapper;
import com.moviereview.api.entity.Movie;
import com.moviereview.api.service.MovieService;
import com.moviereview.api.service.ReviewService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Expose les opérations REST liées aux films.
 */
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final MovieMapper movieMapper;
    private final ReviewMapper reviewMapper;

    /**
     * Crée un nouveau film.
     *
     * @param request les informations du film à enregistrer.
     * @return le film créé avec son identifiant.
     */
    @PostMapping
    public ResponseEntity<MovieResponse> create(@RequestBody MovieCreateRequest request) {
        Movie created = movieService.create(movieMapper.toEntity(request));
        return ResponseEntity
                .created(URI.create("/movies/" + created.getId()))
                .body(movieMapper.toResponse(created));
    }

    /**
     * Récupère tous les films.
     *
     * @return la liste de tous les films.
     */
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAll() {
        return ResponseEntity.ok(movieService.getAll().stream().map(movieMapper::toResponse).toList());
    }

    /**
     * Récupère un film par son identifiant.
     *
     * @param id l'identifiant du film.
     * @return le film correspondant.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movieMapper.toResponse(movieService.getById(id)));
    }

    /**
     * Récupère toutes les reviews associées à un film.
     *
     * @param id l'identifiant du film.
     * @return la liste des reviews du film.
     */
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByMovieId(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getByMovieId(id).stream().map(reviewMapper::toResponse).toList());
    }

    /**
     * Met à jour les champs modifiables d'un film.
     *
     * @param id l'identifiant du film à modifier.
     * @param request les nouvelles valeurs à appliquer.
     * @return le film mis à jour.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id, @RequestBody MoviePatchRequest request) {
        Movie updated = movieService.updateTitleAndDescriptionAndReleaseDate(
                id,
                request.title(),
                request.description(),
                request.releaseDate()
        );
        return ResponseEntity.ok(movieMapper.toResponse(updated));
    }

    /**
     * Supprime un film par son identifiant.
     *
     * @param id l'identifiant du film à supprimer.
     * @return une réponse vide en cas de suppression réussie.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

