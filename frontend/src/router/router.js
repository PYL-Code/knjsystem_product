import { createRouter, createWebHistory } from 'vue-router'
import MyCertList from "../views/MyCertList.vue";
import MyBarcodeList from "../views/MyBarcodeList.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/certlist',
            name: 'certList',
            component: MyCertList
        },
        {
            path: '/barcodelist',
            name: 'barcodelist',
            component: MyBarcodeList
        }
    ]
})

export default router