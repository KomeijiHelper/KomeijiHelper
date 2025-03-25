// src/router/index.js
import { createRouter, createWebHistory } from "vue-router";
import LoginView from "@/views/LoginView.vue";
import AboutView from "@/views/AboutView.vue";
import HomeView from "@/views/HomeView.vue";
import SelectConsultant from "@/views/SelectConsultant.vue";
import AssistantDashboard from "@/views/AssistantDashboard.vue";
import NormalDashboard from "@/views/NormalDashboard.vue";
import ManagerDashboard from "@/views/ManagerDashboard.vue";
import SupervisorDashboard from "@/views/SupervisorDashboard.vue";

const routes = [
    {
        path: "/",
        name: "Home",
        component: HomeView,
    },
    {
        path: "/login",
        name: "Login",
        component: LoginView,
    },
    {
        path: "/about",
        name: "About",
        component: AboutView,
    },
    {
        path: "/select-consultant",
        name: "SelectConsultant",
        component: SelectConsultant,
    },
    {
        path: "/dashboard/assistant",
        name: "assistantDashboard",
        component: AssistantDashboard,
    },
    {
        path: "/dashboard/normal",
        name: "NormalDashboard",
        component: NormalDashboard,
    },
    {
        path: "/dashboard/manager",
        name: "ManagerDashboard",
        component: ManagerDashboard,
    },
    {
        path: "/dashboard/supervisor",
        name: "SupervisorDashboard",
        component: SupervisorDashboard,
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
