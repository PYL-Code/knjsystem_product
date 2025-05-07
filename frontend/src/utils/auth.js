// src/utils/auth.js

const TOKEN_KEY = 'accessToken'; // 👉 토큰 이름 (수정 가능)

export function getToken() {
    return sessionStorage.getItem(TOKEN_KEY);
}

export function setToken(token) {
    sessionStorage.setItem(TOKEN_KEY, token);
}

export function removeToken() {
    sessionStorage.removeItem(TOKEN_KEY);
}
