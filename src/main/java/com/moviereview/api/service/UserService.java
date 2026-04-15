package com.moviereview.api.service;

import com.moviereview.api.entity.User;
import com.moviereview.api.exception.user.UserNotFoundException;
import com.moviereview.api.repository.UserRepository;
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
}
