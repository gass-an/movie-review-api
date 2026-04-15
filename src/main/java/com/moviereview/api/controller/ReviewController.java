package com.moviereview.api.controller;

import com.moviereview.api.dto.review.CreateReviewRequest;
import com.moviereview.api.dto.review.ReviewResponse;
import com.moviereview.api.dto.review.UpdateReviewRequest;
import com.moviereview.api.controller.mapper.ReviewMapper;
import com.moviereview.api.entity.Review;
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
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewMapper.toResponse(reviewService.getById(id)));
    }

    /**
     * Récupère toutes les reviews d'un film.
     *
     * @param movieId l'identifiant du film.
     * @return la liste des reviews du film.
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getByMovieId(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getByMovieId(movieId).stream().map(reviewMapper::toResponse).toList());
    }

    /**
     * Récupère la review d'un utilisateur pour un film donné.
     *
     * @param movieId l'identifiant du film.
     * @param userId l'identifiant de l'utilisateur.
     * @return la review trouvée pour ce couple film/utilisateur.
     */
    @GetMapping("/movie/{movieId}/user/{userId}")
    public ResponseEntity<ReviewResponse> getByMovieIdAndUserId(@PathVariable Long movieId, @PathVariable Long userId) {
        return ResponseEntity.ok(reviewMapper.toResponse(reviewService.getByMovieIdAndUserId(movieId, userId)));
    }

    /**
     * Met à jour la note et le commentaire d'une review.
     *
     * @param id l'identifiant de la review.
     * @param request les nouvelles valeurs à appliquer.
     * @return la review mise à jour.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(@PathVariable Long id, @RequestBody UpdateReviewRequest request) {
        Review updated = reviewService.updateRatingAndComment(id, request.rating(), request.comment());
        return ResponseEntity.ok(reviewMapper.toResponse(updated));
    }

    /**
     * Supprime une review par son identifiant.
     *
     * @param id l'identifiant de la review à supprimer.
     * @return une réponse vide en cas de suppression réussie.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

