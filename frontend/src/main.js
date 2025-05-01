import { createApp } from "vue";
import App from "./App.vue";
import router from "./router/router.js"; // Vue Router import

import 'bootstrap/dist/css/bootstrap.min.css';

const app = createApp(App);
app.use(router); // Vue Router 적용
app.mount("#app");