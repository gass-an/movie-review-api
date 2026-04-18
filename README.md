# Movie Review API

## Stack

- Java 17
- Spring Boot 4.0.5

### Dépendances

- Spring Web
- Spring Data JPA
- Springdoc OpenAPI / Swagger UI
- PostgreSQL Driver
- Spring Boot DevTools
- Lombok

---

## Lancer toute la stack avec Docker (DB + API + Front)

### Première fois

```bash
docker compose up --build -d
```

### Lancements suivants

```bash
docker compose up -d
```

Ces commandes lancent :

- PostgreSQL
- l'application Spring Boot
- le front statique

Après démarrage, vérifier :

- Front : `http://localhost:5173/`
- Swagger : `http://localhost:8080/swagger-ui/index.html#/`

---

## Lancer l'application Spring Boot en local (sans Docker)

### Avec IntelliJ
Lancer la classe :
```text
MovieReviewApiApplication
```

---

### Ou en ligne de commande

```bash
./mvnw spring-boot:run
```

---

## Accès

- API : http://localhost:8080
- Swagger UI : http://localhost:8080/swagger-ui/index.html#/
- OpenAPI JSON : http://localhost:8080/v3/api-docs
- Base PostgreSQL : localhost:5433
- Front : http://localhost:5173/

---

## Donnees de base au demarrage

L'application injecte automatiquement des donnees de base (users, movies, reviews)
au demarrage via `DataInitializer`.

- Active par defaut: `app.seed.enabled=true`
- Desactivation possible: `app.seed.enabled=false`

Ce seed est idempotent: les memes donnees ne sont pas dupliquees a chaque relance.

---

## Arrêter la stack

```bash
docker compose down
```