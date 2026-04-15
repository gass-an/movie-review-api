package com.moviereview.api.web.mapper;

import com.moviereview.api.web.dto.user.UserRegisterRequest;
import com.moviereview.api.web.dto.user.UserResponse;
import com.moviereview.api.entity.User;
import org.springframework.stereotype.Component;

/**
 * Convertit les objets liés aux utilisateurs entre entités et DTOs.
 */
@Component
public class UserMapper {

    /**
     * Convertit une requête d'enregistrement en entité utilisateur.
     *
     * @param request la requête d'enregistrement.
     * @return l'entité utilisateur correspondante.
     */
    public User toEntity(UserRegisterRequest request) {
        return User.builder()
                .username(request.username())
                .email(request.email())
                .build();
    }

    /**
     * Convertit une entité utilisateur en réponse API.
     *
     * @param user l'entité utilisateur.
     * @return le DTO de réponse.
     */
    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}

