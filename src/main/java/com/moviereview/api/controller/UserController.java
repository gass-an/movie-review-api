package com.moviereview.api.controller;

import com.moviereview.api.dto.review.ReviewResponse;
import com.moviereview.api.dto.user.UserRegisterRequest;
import com.moviereview.api.dto.user.UserResponse;
import com.moviereview.api.controller.mapper.ReviewMapper;
import com.moviereview.api.controller.mapper.UserMapper;
import com.moviereview.api.entity.User;
import com.moviereview.api.service.UserService;
import com.moviereview.api.service.ReviewService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Expose les opérations REST liées aux utilisateurs.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;

    /**
     * Enregistre un nouvel utilisateur.
     *
     * @param request les informations de l'utilisateur à créer.
     * @return l'utilisateur créé.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        User created = userService.register(userMapper.toEntity(request));
        return ResponseEntity
                .created(URI.create("/users/" + created.getId()))
                .body(userMapper.toResponse(created));
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return la liste de tous les utilisateurs.
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream().map(userMapper::toResponse).toList());
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur.
     * @return l'utilisateur correspondant.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toResponse(userService.getById(id)));
    }

    /**
     * Récupère toutes les reviews associées à un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur.
     * @return la liste des reviews de l'utilisateur.
     */
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getByUserId(id).stream().map(reviewMapper::toResponse).toList());
    }

    /**
     * Récupère un utilisateur à partir de son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur recherché.
     * @return l'utilisateur correspondant.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userMapper.toResponse(userService.getByUsername(username)));
    }
}

