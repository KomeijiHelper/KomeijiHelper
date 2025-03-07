<template>
  <nav class="navbar">
    <router-link to="/" class="nav-link">首页</router-link>
    <span class="separator">|</span>
    <router-link to="/about" class="nav-link">关于</router-link>

    <!-- 用户头像 & 下拉菜单 -->
    <div class="user-dropdown" v-if="loggedIn" @click="toggleDropdown">
      <div class="user-icon">{{ userName.charAt(0).toUpperCase() }}</div>
      <div v-if="dropdownOpen" class="dropdown-menu">
        <router-link :to="`/user/${userName}`" class="dropdown-item">用户主页</router-link>
        <div class="dropdown-item logout" @click="logout">登出</div>
      </div>
    </div>

    <!-- 登录按钮 -->
    <div class="user-dropdown" v-else>
      <router-link to="/login" class="user-icon">
        <i class="fas fa-sign-in-alt"></i>
      </router-link>
    </div>

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
const dropdownOpen = ref(false);

watch(
    () => route.path,
    (newPath) => {
      if (newPath === "/login" || newPath === "/") return;
      handleRouteChange();
    }
);

async function handleRouteChange() {
  try {
    await userApi.checkSession();
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
  document.addEventListener("click", closeDropdownOnOutsideClick);
});

onUnmounted(() => {
  window.removeEventListener("storage", updateFromStorage);
  document.removeEventListener("click", closeDropdownOnOutsideClick);
});

// 切换下拉菜单状态
function toggleDropdown(event) {
  event.stopPropagation(); // 防止点击事件冒泡
  dropdownOpen.value = !dropdownOpen.value;
}

// 点击外部关闭下拉菜单
function closeDropdownOnOutsideClick(event) {
  if (!event.target.closest(".user-dropdown")) {
    dropdownOpen.value = false;
  }
}

// 登出逻辑
function logout() {
  userApi.logout();
  loggedIn.value = false;
  userName.value = "";
  localStorage.removeItem("userName");
  dropdownOpen.value = false;
}
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

/* 用户头像 */
.user-dropdown {
  position: absolute;
  right: 20px;
  cursor: pointer;
}

.user-icon {
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

/* 下拉菜单 */
.dropdown-menu {
  position: absolute;
  top: 50px;
  right: 0;
  background: white;
  border-radius: 5px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  min-width: 120px;
  overflow: hidden;
}

.dropdown-item {
  padding: 10px;
  color: black;
  text-decoration: none;
  display: block;
  text-align: center;
  transition: background-color 0.3s;
}

.dropdown-item:hover {
  background-color: #f0f0f0;
}

/* 登出按钮 */
.logout {
  cursor: pointer;
  color: red;
}
</style>
