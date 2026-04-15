package com.moviereview.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot Movie Review API.
 */
@SpringBootApplication
public class MovieReviewApiApplication {

    /**
     * Lance l'application Spring Boot.
     *
     * @param args arguments de ligne de commande transmis au démarrage.
     */
    public static void main(String[] args) {
        SpringApplication.run(MovieReviewApiApplication.class, args);
    }

}
