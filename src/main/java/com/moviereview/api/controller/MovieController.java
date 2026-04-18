package com.moviereview.api.controller;

import com.moviereview.api.web.dto.movie.MovieCreateRequest;
import com.moviereview.api.web.dto.movie.MoviePatchRequest;
import com.moviereview.api.web.dto.movie.MovieResponse;
import com.moviereview.api.web.dto.review.ReviewResponse;
import com.moviereview.api.web.mapper.MovieMapper;
import com.moviereview.api.web.mapper.ReviewMapper;
import com.moviereview.api.entity.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Movies", description = "Opérations de gestion des films")
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
    @Operation(summary = "Créer un film", description = "Crée un nouveau film et retourne la ressource créée.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Film créé"),
            @ApiResponse(responseCode = "400", description = "Payload invalide", content = @Content),
            @ApiResponse(responseCode = "409", description = "Film déjà existant (même titre/date)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
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
    @Operation(summary = "Lister les films", description = "Retourne tous les films disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des films récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAll() {
        return ResponseEntity.ok(movieService.getAll().stream().map(movieMapper::toResponse).toList());
    }

    /**
     * Recherche les films par titre.
     *
     * @param title le fragment de titre recherché.
     * @return la liste des films correspondants.
     */
    @Operation(summary = "Rechercher des films par titre", description = "Retourne les films dont le titre contient la valeur fournie.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Résultats de recherche récupérés"),
            @ApiResponse(responseCode = "400", description = "Paramètre title invalide", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> searchByTitle(
            @Parameter(description = "Fragment de titre à rechercher", example = "matrix")
            @RequestParam String title
    ) {
        return ResponseEntity.ok(movieService.searchByTitle(title).stream().map(movieMapper::toResponse).toList());
    }

    /**
     * Récupère un film par son identifiant.
     *
     * @param id l'identifiant du film.
     * @return le film correspondant.
     */
    @Operation(summary = "Récupérer un film", description = "Récupère un film à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Film trouvé"),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById(@Parameter(description = "Identifiant du film", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(movieMapper.toResponse(movieService.getById(id)));
    }

    /**
     * Récupère toutes les reviews associées à un film.
     *
     * @param id l'identifiant du film.
     * @return la liste des reviews du film.
     */
    @Operation(summary = "Lister les reviews d'un film", description = "Retourne toutes les reviews associées à un film.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des reviews récupérée"),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByMovieId(@Parameter(description = "Identifiant du film", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getByMovieId(id).stream().map(reviewMapper::toResponse).toList());
    }

    /**
     * Met à jour les champs modifiables d'un film.
     *
     * @param id l'identifiant du film à modifier.
     * @param request les nouvelles valeurs à appliquer.
     * @return le film mis à jour.
     */
    @Operation(summary = "Mettre à jour un film", description = "Met à jour partiellement un film existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Film mis à jour"),
            @ApiResponse(responseCode = "400", description = "Payload invalide", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflit de doublon film (même titre/date)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponse> update(@Parameter(description = "Identifiant du film", example = "1") @PathVariable Long id, @RequestBody MoviePatchRequest request) {
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
    @Operation(summary = "Supprimer un film", description = "Supprime un film à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Film supprimé"),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant du film", example = "1") @PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

