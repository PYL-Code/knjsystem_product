import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        user: JSON.parse(sessionStorage.getItem('user')) || null
    }),
    actions: {
        setUser(userData) {
            this.user = userData;
            sessionStorage.setItem('user', JSON.stringify(userData));
        },
        logout() {
            this.user = null;
            sessionStorage.removeItem('user');
        }
    }
});