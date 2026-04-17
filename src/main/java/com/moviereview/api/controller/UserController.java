package com.moviereview.api.controller;

import com.moviereview.api.web.dto.review.ReviewResponse;
import com.moviereview.api.web.dto.user.UserRegisterRequest;
import com.moviereview.api.web.dto.user.UserResponse;
import com.moviereview.api.web.dto.user.UserUpdateRequest;
import com.moviereview.api.web.mapper.ReviewMapper;
import com.moviereview.api.web.mapper.UserMapper;
import com.moviereview.api.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users", description = "Opérations de gestion des utilisateurs")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;

    /**
     * Récupère tous les utilisateurs.
     *
     * @return la liste de tous les utilisateurs.
     */
    @Operation(summary = "Lister les utilisateurs", description = "Retourne tous les utilisateurs enregistrés.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream().map(userMapper::toResponse).toList());
    }

    /**
     * Enregistre un nouvel utilisateur.
     *
     * @param request les informations de l'utilisateur à créer.
     * @return l'utilisateur créé.
     */
    @Operation(summary = "Enregistrer un utilisateur", description = "Crée un nouvel utilisateur et retourne la ressource créée.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Utilisateur créé"),
            @ApiResponse(responseCode = "400", description = "Payload invalide", content = @Content),
            @ApiResponse(responseCode = "409", description = "Utilisateur déjà existant", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        User created = userService.register(userMapper.toEntity(request));
        return ResponseEntity
                .created(URI.create("/users/" + created.getId()))
                .body(userMapper.toResponse(created));
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur.
     * @return l'utilisateur correspondant.
     */
    @Operation(summary = "Récupérer un utilisateur", description = "Récupère un utilisateur à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@Parameter(description = "Identifiant de l'utilisateur", example = "2") @PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toResponse(userService.getById(id)));
    }

    /**
     * Récupère toutes les reviews associées à un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur.
     * @return la liste des reviews de l'utilisateur.
     */
    @Operation(summary = "Lister les reviews d'un utilisateur", description = "Retourne toutes les reviews associées à un utilisateur.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des reviews récupérée"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@Parameter(description = "Identifiant de l'utilisateur", example = "2") @PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getByUserId(id).stream().map(reviewMapper::toResponse).toList());
    }

    /**
     * Récupère un utilisateur à partir de son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur recherché.
     * @return l'utilisateur correspondant.
     */
    @Operation(summary = "Récupérer un utilisateur par username", description = "Récupère un utilisateur à partir de son nom d'utilisateur.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<UserResponse> getByUsername(
            @Parameter(description = "Nom d'utilisateur", example = "JoeDalton")
            @RequestParam String username
    ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.getByUsername(username)));
    }

    /**
     * Met à jour partiellement un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur.
     * @param request les nouvelles valeurs à appliquer.
     * @return l'utilisateur mis à jour.
     */
    @Operation(summary = "Mettre à jour un utilisateur", description = "Met à jour partiellement un utilisateur existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "400", description = "Payload invalide", content = @Content),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable", content = @Content),
            @ApiResponse(responseCode = "409", description = "Utilisateur déjà existant", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @Parameter(description = "Identifiant de l'utilisateur", example = "2") @PathVariable Long id,
            @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.update(id, request)));
    }

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur.
     * @return une réponse vide en cas de suppression réussie.
     */
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur serveur", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant de l'utilisateur", example = "2") @PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

