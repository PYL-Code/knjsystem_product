import { createApp } from "vue";
import App from "./App.vue";
import router from "./router/router.js"; // Vue Router import
import { createPinia } from 'pinia'     // ✅ Pinia 임포트

// import 'bootstrap/dist/css/bootstrap.min.css';

const app = createApp(App);
const pinia = createPinia()

app.use(pinia);
app.use(router); // Vue Router 적용
app.mount("#app");