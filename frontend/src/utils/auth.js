// src/utils/auth.js

const TOKEN_KEY = 'accessToken'; // 👉 토큰 이름 (수정 가능)

export function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

export function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
}

export function removeToken() {
    localStorage.removeItem(TOKEN_KEY);
}
