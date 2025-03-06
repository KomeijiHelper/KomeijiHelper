<template>
  <nav class="navbar">
    <router-link to="/" class="nav-link">首页</router-link>
    <span class="separator">|</span>
    <router-link to="/about" class="nav-link">关于</router-link>
  </nav>
  <router-view></router-view>
</template>

<script setup>

import {onMounted, onUnmounted, ref, watch} from "vue";
import userApi from "@/api/userApi.js";
import {useRoute} from "vue-router";

// 初始化对路径变化的监听, 用于更新校验 session
const route = useRoute();
watch(() => route.path, (newPath, oldPath) => {
  // 如果前往登录页面, 则不 check session
  if (newPath === '/login') { return }
  handleRouteChange();
});
function handleRouteChange() {
  try {
    userApi.checkSession()
    loggedIn = true
  } catch (e) {
    loggedIn = false
  }
}

// 初始化对用户名的监听, 用于绘制AppBar
let loggedIn = false;
localStorage.setItem('userName', '');
const userName = ref(localStorage.getItem('userName') || '');
const updateFromStorage = (event) => {
  if (event.key === 'userName') {
    userName.value = event.newValue || '';
  }
};
onMounted(() => {
  window.addEventListener('storage', updateFromStorage);
});
onUnmounted(() => {
  window.removeEventListener('storage', updateFromStorage);
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
</style>
