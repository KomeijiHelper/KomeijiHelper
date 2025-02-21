import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

const app = createApp(App);
app.use(router);
app.use(store);
app.mount("#app");

const userId = localStorage.getItem('userId');
if (userId) {
    store.commit('user/SET_USER_ID', userId);
}