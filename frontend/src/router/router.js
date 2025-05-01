import { createRouter, createWebHistory } from 'vue-router'
import MyCertList from "../views/MyCertList.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/certlist',
            name: 'certList',
            component: MyCertList
        }
    ]
})

export default router