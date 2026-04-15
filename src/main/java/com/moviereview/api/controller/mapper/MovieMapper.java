package com.moviereview.api.controller.mapper;

import com.moviereview.api.dto.movie.MovieCreateRequest;
import com.moviereview.api.dto.movie.MovieResponse;
import com.moviereview.api.entity.Movie;
import org.springframework.stereotype.Component;

/**
 * Convertit les objets liés aux films entre entités et DTOs.
 */
@Component
public class MovieMapper {

    /**
     * Convertit une requête de création en entité.
     *
     * @param request la requête de création.
     * @return l'entité film correspondante.
     */
    public Movie toEntity(MovieCreateRequest request) {
        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .build();
    }

    /**
     * Convertit une entité film en réponse API.
     *
     * @param movie l'entité film.
     * @return le DTO de réponse.
     */
    public MovieResponse toResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getReleaseDate(),
                movie.getCreatedAt(),
                movie.getUpdatedAt()
        );
    }
}

