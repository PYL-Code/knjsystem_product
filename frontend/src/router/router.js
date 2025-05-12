import { createRouter, createWebHistory } from 'vue-router'
import MyCertList from "../views/MyCertList.vue";
import MyBarcodeList from "../views/MyBarcodeList.vue";
import BarcodeDetail from "@/views/BarcodeDetail.vue";
import BarcodeAddCertList from "@/views/BarcodeAddCertList.vue";
import BarcodeInsertForm from "@/views/BarcodeInsertForm.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
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
            path: '/barcode/update/cert',
            name: 'barcodeAddCertList',
            component: BarcodeAddCertList
        },
        {
            path: '/barcode/insert',
            name: 'barcodeInsert',
            component: BarcodeInsertForm
        }
    ]
})

export default router