# Front de test API

Petit front HTML/CSS/JS pur pour tester l'API `movie-review-api` sans framework.

## Structure

```text
front/
  index.html
  styles.css
  app.js
```

## Lancer

Depuis la racine du projet :

```bash
cd /home/anthony/dev/sem2/movie-review-api/front
python3 -m http.server 5173
```

Puis ouvrir :

```text
http://localhost:5173
```

Par défaut, le front appelle l'API sur :

```text
http://localhost:8080
```

Tu peux changer cette URL directement dans l'interface.

## Endpoints couverts

- `GET /movies`
- `GET /movies/search?title=...`
- `POST /movies`
- `PATCH /movies/{id}`
- `DELETE /movies/{id}`
- `GET /users`
- `GET /users/search?username=...`
- `POST /users/register`
- `PATCH /users/{id}`
- `DELETE /users/{id}`
- `GET /reviews`
- `GET /reviews/search?movieId=...&userId=...`
- `POST /reviews`
- `PATCH /reviews/{id}`
- `DELETE /reviews/{id}`

