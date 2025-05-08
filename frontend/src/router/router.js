import { createRouter, createWebHistory } from 'vue-router'
import MyCertList from "../views/MyCertList.vue";
import MyBarcodeList from "../views/MyBarcodeList.vue";
import BarcodeDetail from "@/views/BarcodeDetail.vue";
import BarcodeAddCertList from "@/views/BarcodeAddCertList.vue";

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
        },
        {
            path: '/barcode/cert/update',
            name: 'barcodeAddCertList',
            component: BarcodeAddCertList
        }
    ]
})

export default router