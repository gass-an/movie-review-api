# Movie Review API

## Stack

- Java 17
- Spring Boot 4.0.5

### Dépendances

- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Spring Boot DevTools
- Lombok

---

## Lancer la base PostgreSQL

```bash
docker compose up
```

---

## Lancer l'application

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
- Base PostgreSQL : localhost:5433

---

## Arrêter la base

```bash
docker compose down
```