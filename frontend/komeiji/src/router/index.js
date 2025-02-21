// src/router/index.js
import { createRouter, createWebHistory } from "vue-router";
import LoginView from "@/views/LoginView.vue";
import AboutView from "@/views/AboutView.vue";

const routes = [
    {
        path: "/",
        name: "Home",
        component: LoginView,
    },
    {
        path: "/about",
        name: "About",
        component: AboutView,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
