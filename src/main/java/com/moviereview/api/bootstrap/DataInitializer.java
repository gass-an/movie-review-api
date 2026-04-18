package com.moviereview.api.bootstrap;

import com.moviereview.api.entity.Movie;
import com.moviereview.api.entity.Review;
import com.moviereview.api.entity.User;
import com.moviereview.api.repository.MovieRepository;
import com.moviereview.api.repository.ReviewRepository;
import com.moviereview.api.repository.UserRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Injecte un jeu de donnees de base au démarrage.
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.seed", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {
        User leonardo = findOrCreateUser("Leonardo", "leonardo@ninjaturtle.com");
        User donatello = findOrCreateUser("Donatello", "donatello@ninjaturtle.com");
        User raphael = findOrCreateUser("Raphael", "raphael@ninjaturtle.com");
        User michelangelo = findOrCreateUser("Michelangelo", "michelangelo@ninjaturtle.com");
        User splinter = findOrCreateUser("Splinter", "splinter@ninjaturtle.com");


        // Teenage Mutant Ninja Turtles
        Movie teenageMutantNinjaTurtles = findOrCreateMovie(
                "Teenage Mutant Ninja Turtles",
                "Quatre tortues mutantes fans de pizza sortent des égouts pour distribuer des baffes à des ninjas humains sous la supervision d’un rat sensei.",
                LocalDate.of(1990, 3, 30)
        );

        findOrCreateReview(teenageMutantNinjaTurtles, leonardo, 5, "Je suis super classe dans ce film !");
        findOrCreateReview(teenageMutantNinjaTurtles, donatello, 5, "Notre QG est super réaliste !");
        findOrCreateReview(teenageMutantNinjaTurtles, raphael, 5, "J'aime bien la partie où je tape le méchant !");
        findOrCreateReview(teenageMutantNinjaTurtles, michelangelo, 5, "Les pizzas ont l'air trop bonnes !");
        findOrCreateReview(teenageMutantNinjaTurtles, splinter, 5, "Fier de mes fils !");


        // Le Seigneur des Anneaux
        Movie leSeigneurDesAnneaux = findOrCreateMovie(
                "Le Seigneur des Anneaux : La Communauté de l’Anneau",
                "Un petit gars doit marcher très longtemps pour jeter un bijou dans un volcan pendant que tout le monde essaie de lui voler.",
                LocalDate.of(2001, 12, 19)
        );

        findOrCreateReview(leSeigneurDesAnneaux, leonardo, 5, "Une quête, une équipe, un leader… j’approuve.");
        findOrCreateReview(leSeigneurDesAnneaux, donatello, 4, "Techniquement, ils auraient pu utiliser les aigles plus tôt.");
        findOrCreateReview(leSeigneurDesAnneaux, raphael, 4, "Beaucoup trop de marche, mais au moins ça se bat bien.");
        findOrCreateReview(leSeigneurDesAnneaux, michelangelo, 5, "Ils mangent tout le temps, j’aime bien ces hobbits.");
        findOrCreateReview(leSeigneurDesAnneaux, splinter, 5, "Le pouvoir corrompt, belle leçon.");


        // Harry Potter
        Movie harryPotter = findOrCreateMovie(
                "Harry Potter à l'école des sorciers",
                "Un orphelin découvre qu’il est riche, célèbre et magicien, et qu’il doit aller à l’école pour apprendre à survivre à un prof bizarre.",
                LocalDate.of(2001, 11, 16)
        );

        findOrCreateReview(harryPotter, donatello, 5, "Une école avec escaliers mouvants ? Ingénieux.");
        findOrCreateReview(harryPotter, raphael, 3, "Pourquoi ils règlent pas ça avec un bon combat ?");
        findOrCreateReview(harryPotter, michelangelo, 5, "Ils ont des bonbons magiques, je veux les mêmes !");
        findOrCreateReview(harryPotter, splinter, 5, "Un élève prometteur face à un grand danger.");


        // The Matrix
        Movie matrix = findOrCreateMovie(
                "The Matrix",
                "Un informaticien découvre que tout est faux, avale une pilule douteuse et devient cheaté dans la vraie vie.",
                LocalDate.of(1999, 3, 31)
        );

        findOrCreateReview(matrix, donatello, 5, "Enfin un film qui comprend l’informatique !");
        findOrCreateReview(matrix, leonardo, 4, "Bonne discipline mentale, mais étrange entraînement.");
        findOrCreateReview(matrix, michelangelo, 5, "Esquiver les balles ? Je veux apprendre ça !");
        findOrCreateReview(matrix, raphael, 4, "Pourquoi éviter les balles quand tu peux les renvoyer ?");

    }

    private User findOrCreateUser(String username, String email) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.save(User.builder()
                        .username(username)
                        .email(email)
                        .build()));
    }

    private Movie findOrCreateMovie(String title, String description, LocalDate releaseDate) {
        return movieRepository.findByTitleAndReleaseDate(title, releaseDate)
                .orElseGet(() -> movieRepository.save(Movie.builder()
                        .title(title)
                        .description(description)
                        .releaseDate(releaseDate)
                        .build()));
    }

    private Review findOrCreateReview(Movie movie, User user, Integer rating, String comment) {
        return reviewRepository.findByMovieIdAndUserId(movie.getId(), user.getId())
                .orElseGet(() -> reviewRepository.save(Review.builder()
                        .movie(movie)
                        .user(user)
                        .rating(rating)
                        .comment(comment)
                        .build()));
    }
}

