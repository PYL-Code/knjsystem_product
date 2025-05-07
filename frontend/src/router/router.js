import { createRouter, createWebHistory } from 'vue-router'
import MyCertList from "../views/MyCertList.vue";
import MyBarcodeList from "../views/MyBarcodeList.vue";
import BarcodeDetail from "@/views/BarcodeDetail.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/cert/list',
            name: 'certList',
            component: MyCertList
        },
        {
            path: '/barcode/list',
            name: 'barcodeList',
            component: MyBarcodeList
        },
        {
            path: '/barcode/detail',
            name: 'barcodeDetail',
            component: BarcodeDetail
        }
    ]
})

export default router