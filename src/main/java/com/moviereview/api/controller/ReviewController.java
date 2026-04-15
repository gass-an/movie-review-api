package com.moviereview.api.controller;

import com.moviereview.api.web.dto.review.CreateReviewRequest;
import com.moviereview.api.web.dto.review.ReviewResponse;
import com.moviereview.api.web.dto.review.UpdateReviewRequest;
import com.moviereview.api.web.mapper.ReviewMapper;
import com.moviereview.api.entity.Review;
import com.moviereview.api.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.moviereview.api.service.ReviewService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expose les opérations REST liées aux reviews.
 */
@Tag(name = "Reviews", description = "Opérations de gestion des reviews")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    /**
     * Crée une review pour un film et un utilisateur donnés.
     *
     * @param request les données nécessaires à la création de la review.
     * @return la review créée.
     */
    @Operation(summary = "Créer une review", description = "Crée une review pour un couple film/utilisateur.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review créée"),
            @ApiResponse(responseCode = "400", description = "Payload invalide", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Film ou utilisateur introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Review déjà existante", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ReviewResponse> create(@RequestBody CreateReviewRequest request) {
        Review created = reviewService.create(
                request.movieId(),
                request.userId(),
                request.rating(),
                request.comment()
        );

        return ResponseEntity
                .created(URI.create("/reviews/" + created.getId()))
                .body(reviewMapper.toResponse(created));
    }

    /**
     * Récupère une review par son identifiant.
     *
     * @param id l'identifiant de la review.
     * @return la review correspondante.
     */
    @Operation(summary = "Récupérer une review", description = "Récupère une review à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review trouvée"),
            @ApiResponse(responseCode = "404", description = "Review introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getById(@Parameter(description = "Identifiant de la review", example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(reviewMapper.toResponse(reviewService.getById(id)));
    }

    /**
     * Récupère toutes les reviews d'un film.
     *
     * @param movieId l'identifiant du film.
     * @return la liste des reviews du film.
     */
    @Operation(summary = "Lister les reviews d'un film", description = "Retourne toutes les reviews associées à un film.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des reviews récupérée"),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getByMovieId(@Parameter(description = "Identifiant du film", example = "1") @PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getByMovieId(movieId).stream().map(reviewMapper::toResponse).toList());
    }

    /**
     * Récupère la review d'un utilisateur pour un film donné.
     *
     * @param movieId l'identifiant du film.
     * @param userId l'identifiant de l'utilisateur.
     * @return la review trouvée pour ce couple film/utilisateur.
     */
    @Operation(summary = "Récupérer une review par film et utilisateur", description = "Retourne la review unique pour un couple film/utilisateur.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review trouvée"),
            @ApiResponse(responseCode = "404", description = "Film, utilisateur ou review introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/movie/{movieId}/user/{userId}")
    public ResponseEntity<ReviewResponse> getByMovieIdAndUserId(
            @Parameter(description = "Identifiant du film", example = "1") @PathVariable Long movieId,
            @Parameter(description = "Identifiant de l'utilisateur", example = "2") @PathVariable Long userId
    ) {
        return ResponseEntity.ok(reviewMapper.toResponse(reviewService.getByMovieIdAndUserId(movieId, userId)));
    }

    /**
     * Met à jour la note et le commentaire d'une review.
     *
     * @param id l'identifiant de la review.
     * @param request les nouvelles valeurs à appliquer.
     * @return la review mise à jour.
     */
    @Operation(summary = "Mettre à jour une review", description = "Met à jour partiellement une review existante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review mise à jour"),
            @ApiResponse(responseCode = "400", description = "Payload invalide", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Review introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(
            @Parameter(description = "Identifiant de la review", example = "10") @PathVariable Long id,
            @RequestBody UpdateReviewRequest request
    ) {
        Review updated = reviewService.updateRatingAndComment(id, request.rating(), request.comment());
        return ResponseEntity.ok(reviewMapper.toResponse(updated));
    }

    /**
     * Supprime une review par son identifiant.
     *
     * @param id l'identifiant de la review à supprimer.
     * @return une réponse vide en cas de suppression réussie.
     */
    @Operation(summary = "Supprimer une review", description = "Supprime une review à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review supprimée"),
            @ApiResponse(responseCode = "404", description = "Review introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant de la review", example = "10") @PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

