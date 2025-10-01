const BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8081';

async function request(path, options = {}) {
  const res = await fetch(`${BASE}${path}`, {
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    ...options,
  });
  if (!res.ok) {
    let msg = `${res.status} ${res.statusText}`;
    try { const body = await res.json(); msg = body.error || body.message || msg; } catch {}
    throw new Error(msg);
  }
  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

export const Auth = {
  signup: (payload) => request('/api/auth/signup', { method: 'POST', body: JSON.stringify(payload) }),
  login:  (payload) => request('/api/auth/login',  { method: 'POST', body: JSON.stringify(payload) }),
};

export const Issues = {
  list:   (search) => request(`/api/issues${search ? `?search=${encodeURIComponent(search)}` : ''}`),
  create: (payload) => request('/api/issues', { method: 'POST', body: JSON.stringify(payload) }),
  remove: (id) => request(`/api/issues/${id}`, { method: 'DELETE' }),
};
