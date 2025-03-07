<template>
  <nav class="navbar">
    <router-link to="/" class="nav-link">首页</router-link>
    <span class="separator">|</span>
    <router-link to="/about" class="nav-link">关于</router-link>

    <!-- 右侧用户图标 -->
    <router-link
        v-if="loggedIn"
        :to="`/user/${userName}`"
        class="user-icon"
    >
      {{ userName.charAt(0).toUpperCase() }}
    </router-link>
    <router-link v-else to="/login" class="user-icon">
      <i class="fas fa-sign-in-alt"></i>
    </router-link>
  </nav>
  <router-view></router-view>
</template>

<script setup>
import { onMounted, onUnmounted, ref, watch } from "vue";
import userApi from "@/api/userApi.js";
import { useRoute } from "vue-router";

// 监听路径变化，检查 session
const route = useRoute();
const loggedIn = ref(false);
const userName = ref(localStorage.getItem("userName") || "");

watch(
    () => route.path,
    (newPath) => {
      if (newPath === "/login") return;
      handleRouteChange();
    }
);

function handleRouteChange() {
  try {
    userApi.checkSession();
    loggedIn.value = true;
  } catch (e) {
    loggedIn.value = false;
  }
}

// 监听 localStorage 变化
const updateFromStorage = (event) => {
  if (event.key === "userName") {
    userName.value = event.newValue || "";
  }
};

onMounted(() => {
  window.addEventListener("storage", updateFromStorage);
});

onUnmounted(() => {
  window.removeEventListener("storage", updateFromStorage);
});
</script>

<style scoped>
.navbar {
  background-color: #333;
  padding: 10px 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.nav-link {
  color: #fff;
  text-decoration: none;
  font-size: 16px;
  padding: 10px;
  margin: 0 10px;
  border-radius: 5px;
  transition: background-color 0.3s, color 0.3s;
}

.nav-link:hover {
  background-color: #007bff;
  color: #fff;
}

.separator {
  color: #fff;
  margin: 0 10px;
}

/* 用户图标 */
.user-icon {
  margin-left: auto;
  width: 40px;
  height: 40px;
  background-color: #007bff;
  color: white;
  font-size: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  text-decoration: none;
  font-weight: bold;
  transition: background-color 0.3s;
}

.user-icon:hover {
  background-color: #0056b3;
}

/* 登录图标样式 */
.user-icon i {
  font-size: 20px;
}
</style>
