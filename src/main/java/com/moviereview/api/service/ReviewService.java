package com.moviereview.api.service;

import com.moviereview.api.entity.Movie;
import com.moviereview.api.entity.Review;
import com.moviereview.api.entity.User;
import com.moviereview.api.exception.review.InvalidReviewRatingException;
import com.moviereview.api.exception.review.ReviewAlreadyExistsException;
import com.moviereview.api.exception.review.ReviewNotFoundException;
import com.moviereview.api.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Contient la logique métier liée aux reviews.
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieService movieService;
    private final UserService userService;

    /**
     * Crée une review pour un film et un utilisateur donnés.
     *
     * @param movieId l'identifiant du film.
     * @param userId l'identifiant de l'utilisateur.
     * @param rating la note attribuée.
     * @param comment le commentaire associé.
     * @return la review créée.
     */
    public Review create(Long movieId, Long userId, Integer rating, String comment) {
        validateRating(rating);

        if (reviewRepository.findByMovieIdAndUserId(movieId, userId).isPresent()) {
            throw ReviewAlreadyExistsException.byMovieAndUser(movieId, userId);
        }

        Movie movie = movieService.getById(movieId);
        User user = userService.getById(userId);

        Review review = Review.builder()
                .movie(movie)
                .user(user)
                .rating(rating)
                .comment(comment)
                .build();

        return reviewRepository.save(review);
    }

    /**
     * Récupère toutes les reviews liées à un film.
     *
     * @param movieId l'identifiant du film.
     * @return la liste des reviews du film.
     */
    public List<Review> getByMovieId(Long movieId) {
        movieService.getById(movieId);
        return reviewRepository.findByMovieId(movieId);
    }

    /**
     * Récupère toutes les reviews liées à un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur.
     * @return la liste des reviews de l'utilisateur.
     */
    public List<Review> getByUserId(Long userId) {
        userService.getById(userId);
        return reviewRepository.findByUserId(userId);
    }

    /**
     * Récupère une review par son identifiant.
     *
     * @param id l'identifiant de la review.
     * @return la review trouvée.
     */
    public Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> ReviewNotFoundException.byId(id));
    }

    /**
     * Récupère une review à partir d'un couple film/utilisateur.
     *
     * @param movieId l'identifiant du film.
     * @param userId l'identifiant de l'utilisateur.
     * @return la review correspondante.
     */
    public Review getByMovieIdAndUserId(Long movieId, Long userId) {
        movieService.getById(movieId);
        userService.getById(userId);

        return reviewRepository.findByMovieIdAndUserId(movieId, userId)
                .orElseThrow(() -> ReviewNotFoundException.byMovieAndUser(movieId, userId));
    }

    /**
     * Met à jour la note et le commentaire d'une review.
     *
     * @param id l'identifiant de la review.
     * @param rating la nouvelle note, si présente.
     * @param comment le nouveau commentaire, si présent.
     * @return la review mise à jour.
     */
    public Review updateRatingAndComment(Long id, Integer rating, String comment) {
        if (rating != null) {
            validateRating(rating);
        }

        Review review = getById(id);

        if (rating != null) {
            review.setRating(rating);
        }
        if (comment != null) {
            review.setComment(comment);
        }

        return reviewRepository.save(review);
    }

    /**
     * Supprime une review par son identifiant.
     *
     * @param id l'identifiant de la review à supprimer.
     */
    public void deleteById(Long id) {
        Review review = getById(id);
        reviewRepository.delete(review);
    }

    /**
     * Vérifie qu'une note est comprise entre 1 et 5.
     *
     * @param rating la note à valider.
     */
    private void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new InvalidReviewRatingException(rating);
        }
    }
}

