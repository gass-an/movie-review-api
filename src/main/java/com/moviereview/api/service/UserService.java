package com.moviereview.api.service;

import com.moviereview.api.entity.User;
import com.moviereview.api.exception.user.UserAlreadyExistsException;
import com.moviereview.api.exception.user.UserNotFoundException;
import com.moviereview.api.repository.UserRepository;
import com.moviereview.api.web.dto.user.UserUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Contient la logique métier liée aux utilisateurs.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Enregistre un nouvel utilisateur.
     *
     * @param user l'utilisateur à créer.
     * @return l'utilisateur enregistré.
     */
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw UserAlreadyExistsException.byUsername(user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw UserAlreadyExistsException.byEmail(user.getEmail());
        }
        return userRepository.save(user);
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return la liste de tous les utilisateurs.
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur.
     * @return l'utilisateur trouvé.
     */
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));
    }

    /**
     * Récupère un utilisateur par son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur recherché.
     * @return l'utilisateur trouvé.
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UserNotFoundException.byUsername(username));
    }

    /**
     * Met à jour partiellement un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur.
     * @param request les nouvelles valeurs à appliquer.
     * @return l'utilisateur mis à jour.
     */
    public User update(Long id, UserUpdateRequest request) {
        User user = getById(id);

        if (request.username() != null && userRepository.existsByUsernameAndIdNot(request.username(), id)) {
            throw UserAlreadyExistsException.byUsername(request.username());
        }
        if (request.email() != null && userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw UserAlreadyExistsException.byEmail(request.email());
        }

        if (request.username() != null) {
            user.setUsername(request.username());
        }
        if (request.email() != null) {
            user.setEmail(request.email());
        }

        return userRepository.save(user);
    }

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur.
     */
    public void deleteById(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }
}
