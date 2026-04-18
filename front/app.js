const DEFAULT_API_BASE = 'http://localhost:8080';
const state = {
  apiBase: localStorage.getItem('movie-review-api-base') || DEFAULT_API_BASE,
  lastData: null,
};

const els = {};

document.addEventListener('DOMContentLoaded', () => {
  bindEls();
  setupApiBase();
  bindForms();
  bindActions();
  renderBaseUrl();
  log('Prêt. Lance l’API puis teste les formulaires.');
});

function bindEls() {
  [
    'apiBase', 'saveApiBase', 'log',
    'loadMovies', 'clearMovies', 'moviesResult', 'movieCreateForm', 'movieSearchForm', 'moviePatchForm', 'movieDeleteForm',
    'loadUsers', 'clearUsers', 'usersResult', 'userRegisterForm', 'userSearchForm', 'userPatchForm', 'userDeleteForm',
    'loadReviews', 'clearReviews', 'reviewsResult', 'reviewCreateForm', 'reviewSearchForm', 'reviewPatchForm', 'reviewDeleteForm',
    'reviewByMovieForm', 'reviewByUserForm',
    'clearLog'
  ].forEach((id) => { els[id] = document.getElementById(id); });
}

function setupApiBase() {
  els.apiBase.value = state.apiBase;
  els.saveApiBase.addEventListener('click', () => {
    state.apiBase = els.apiBase.value.trim() || DEFAULT_API_BASE;
    localStorage.setItem('movie-review-api-base', state.apiBase);
    renderBaseUrl();
    log(`API base URL enregistrée: ${state.apiBase}`, 'ok');
  });
}

function renderBaseUrl() {
  els.log.dataset.baseUrl = state.apiBase;
}

function bindActions() {
  els.loadMovies.addEventListener('click', () => loadMovies());
  els.clearMovies.addEventListener('click', () => clearResult('moviesResult'));
  els.loadUsers.addEventListener('click', () => loadUsers());
  els.clearUsers.addEventListener('click', () => clearResult('usersResult'));
  els.loadReviews.addEventListener('click', () => loadReviews());
  els.clearReviews.addEventListener('click', () => clearResult('reviewsResult'));
  els.clearLog.addEventListener('click', () => { els.log.textContent = 'Prêt.'; });
}

function bindForms() {
  els.movieCreateForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = new FormData(e.currentTarget);
    const payload = {
      title: form.get('title')?.toString().trim(),
      description: form.get('description')?.toString().trim() || null,
      releaseDate: form.get('releaseDate')?.toString() || null,
    };
    await send('POST', '/movies', payload, 'moviesResult', loadMovies);
    e.currentTarget.reset();
  });

  els.movieSearchForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const title = new FormData(e.currentTarget).get('title')?.toString().trim();
    await loadMovies(`/movies/search?title=${encodeURIComponent(title)}`);
  });

  els.moviePatchForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = new FormData(e.currentTarget);
    const id = form.get('id')?.toString().trim();
    const payload = {
      title: form.get('title')?.toString().trim() || null,
      description: form.get('description')?.toString().trim() || null,
      releaseDate: form.get('releaseDate')?.toString() || null,
    };
    await send('PATCH', `/movies/${id}`, payload, 'moviesResult', loadMovies);
  });

  els.movieDeleteForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = new FormData(e.currentTarget).get('id')?.toString().trim();
    await send('DELETE', `/movies/${id}`, null, 'moviesResult', loadMovies, false);
    e.currentTarget.reset();
  });

  els.userRegisterForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = new FormData(e.currentTarget);
    const payload = {
      username: form.get('username')?.toString().trim(),
      email: form.get('email')?.toString().trim(),
    };
    await send('POST', '/users/register', payload, 'usersResult', loadUsers);
    e.currentTarget.reset();
  });

  els.userSearchForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = new FormData(e.currentTarget).get('username')?.toString().trim();
    await loadUsers(`/users/search?username=${encodeURIComponent(username)}`);
  });

  els.userPatchForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = new FormData(e.currentTarget);
    const id = form.get('id')?.toString().trim();
    const payload = {
      username: form.get('username')?.toString().trim() || null,
      email: form.get('email')?.toString().trim() || null,
    };
    await send('PATCH', `/users/${id}`, payload, 'usersResult', loadUsers);
  });

  els.userDeleteForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = new FormData(e.currentTarget).get('id')?.toString().trim();
    await send('DELETE', `/users/${id}`, null, 'usersResult', loadUsers, false);
    e.currentTarget.reset();
  });

  els.reviewCreateForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = new FormData(e.currentTarget);
    const payload = {
      movieId: numberOrNull(form.get('movieId')),
      userId: numberOrNull(form.get('userId')),
      rating: numberOrNull(form.get('rating')),
      comment: form.get('comment')?.toString().trim() || null,
    };
    await send('POST', '/reviews', payload, 'reviewsResult', loadReviews);
    e.currentTarget.reset();
  });

  els.reviewSearchForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = new FormData(e.currentTarget);
    const movieId = form.get('movieId')?.toString().trim();
    const userId = form.get('userId')?.toString().trim();
    await loadReviews(`/reviews/search?movieId=${encodeURIComponent(movieId)}&userId=${encodeURIComponent(userId)}`);
  });

  els.reviewByMovieForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const movieId = new FormData(e.currentTarget).get('movieId')?.toString().trim();
    await loadReviews(`/movies/${encodeURIComponent(movieId)}/reviews`);
  });

  els.reviewByUserForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const userId = new FormData(e.currentTarget).get('userId')?.toString().trim();
    await loadReviews(`/users/${encodeURIComponent(userId)}/reviews`);
  });

  els.reviewPatchForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = new FormData(e.currentTarget);
    const id = form.get('id')?.toString().trim();
    const payload = {
      rating: form.get('rating')?.toString().trim() ? Number(form.get('rating')) : null,
      comment: form.get('comment')?.toString().trim() || null,
    };
    await send('PATCH', `/reviews/${id}`, payload, 'reviewsResult', loadReviews);
  });

  els.reviewDeleteForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = new FormData(e.currentTarget).get('id')?.toString().trim();
    await send('DELETE', `/reviews/${id}`, null, 'reviewsResult', loadReviews, false);
    e.currentTarget.reset();
  });
}

async function loadMovies(path = '/movies') {
  return loadList(path, 'moviesResult', 'Films');
}

async function loadUsers(path = '/users') {
  return loadList(path, 'usersResult', 'Utilisateurs');
}

async function loadReviews(path = '/reviews') {
  return loadList(path, 'reviewsResult', 'Reviews');
}

async function loadList(path, resultId, title) {
  try {
    setResult(resultId, `<div class="item"><strong>${title}</strong><div class="item__meta">Chargement...</div></div>`);
    const data = await request(path, { method: 'GET' });
    renderCollection(resultId, title, data);
    log(`${path} -> OK`, 'ok');
    return data;
  } catch (error) {
    renderError(resultId, error);
    log(`${path} -> ${error.message}`, 'error');
  }
}

async function send(method, path, body, resultId, refreshFn, renderBody = true) {
  try {
    const data = await request(path, { method, body });
    if (renderBody && data !== null && data !== undefined) {
      renderItem(resultId, data, `Réponse ${method} ${path}`);
    } else {
      setResult(resultId, `<div class="item"><strong>${method} ${path}</strong><div class="ok">Action réussie</div></div>`);
    }
    log(`${method} ${path} -> OK`, 'ok');
    if (refreshFn) await refreshFn();
    return data;
  } catch (error) {
    renderError(resultId, error);
    log(`${method} ${path} -> ${error.message}`, 'error');
  }
}

async function request(path, options = {}) {
  const url = `${state.apiBase}${path}`;
  const init = {
    method: options.method || 'GET',
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
  };
  if (options.body !== undefined && options.body !== null && init.method !== 'GET') {
    init.body = JSON.stringify(options.body);
  }

  const response = await fetch(url, init);
  const contentType = response.headers.get('content-type') || '';
  const isJson = contentType.includes('application/json');
  const data = isJson ? await response.json().catch(() => null) : await response.text().catch(() => null);

  if (!response.ok) {
    const message = (data && typeof data === 'object' && data.message) ? data.message : (typeof data === 'string' && data) || `HTTP ${response.status}`;
    const code = (data && typeof data === 'object' && data.code) ? data.code : 'HTTP_ERROR';
    throw { status: response.status, code, message, data };
  }

  if (response.status === 204) return null;
  return data;
}

function renderCollection(resultId, title, data) {
  const items = Array.isArray(data) ? data : (data ? [data] : []);
  if (items.length === 0) {
    setResult(resultId, `<div class="item"><strong>${title}</strong><div class="item__meta">Aucun résultat</div></div>`);
    return;
  }
  setResult(resultId, `
    <div class="item">
      <h4>${title}</h4>
      <div class="list">
        ${items.map(renderEntityCard).join('')}
      </div>
    </div>
  `);
}

function renderItem(resultId, data, title = 'Résultat') {
  setResult(resultId, `
    <div class="item">
      <h4>${title}</h4>
      ${renderEntityCard(data)}
    </div>
  `);
}

function renderEntityCard(entity) {
  if (entity === null || entity === undefined) return '<div class="item">(vide)</div>';
  if (typeof entity !== 'object') return `<div class="item">${escapeHtml(String(entity))}</div>`;

  const entries = Object.entries(entity)
    .map(([key, value]) => `<div><strong>${escapeHtml(key)}:</strong> ${formatValue(value)}</div>`)
    .join('');

  return `<div class="item">${entries}</div>`;
}

function formatValue(value) {
  if (value === null || value === undefined) return '<em>null</em>';
  if (Array.isArray(value)) {
    return `<pre>${escapeHtml(JSON.stringify(value, null, 2))}</pre>`;
  }
  if (typeof value === 'object') {
    return `<pre>${escapeHtml(JSON.stringify(value, null, 2))}</pre>`;
  }
  return escapeHtml(String(value));
}

function renderError(resultId, error) {
  const code = error.code || 'ERROR';
  const message = error.message || 'Erreur inconnue';
  const status = error.status ? `HTTP ${error.status}` : '';
  setResult(resultId, `
    <div class="item error">
      <strong>${code}</strong>
      <div>${escapeHtml(message)}</div>
      <div class="item__meta">${status}</div>
    </div>
  `);
}

function setResult(resultId, html) {
  els[resultId].innerHTML = html;
}

function clearResult(resultId) {
  els[resultId].innerHTML = '';
}

function log(message, level = '') {
  const prefix = level === 'error' ? '✖ ' : level === 'ok' ? '✔ ' : '• ';
  const time = new Date().toLocaleTimeString();
  els.log.textContent = `[${time}] ${prefix}${message}\n\n${els.log.textContent.replace(/^Prêt\.\n?/, '')}`.trim();
}

function numberOrNull(value) {
  const n = Number(value);
  return Number.isFinite(n) ? n : null;
}

function escapeHtml(text) {
  return text
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;')
    .replaceAll("'", '&#39;');
}

