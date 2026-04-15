package com.moviereview.api.web.mapper;

import com.moviereview.api.web.dto.review.ReviewResponse;
import com.moviereview.api.entity.Review;
import org.springframework.stereotype.Component;

/**
 * Convertit les objets liés aux reviews entre entités et DTOs.
 */
@Component
public class ReviewMapper {

    /**
     * Convertit une entité review en réponse API.
     *
     * @param review l'entité review.
     * @return le DTO de réponse.
     */
    public ReviewResponse toResponse(Review review) {
        Long movieId = review.getMovie() != null ? review.getMovie().getId() : null;
        Long userId = review.getUser() != null ? review.getUser().getId() : null;

        return new ReviewResponse(
                review.getId(),
                movieId,
                userId,
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}

