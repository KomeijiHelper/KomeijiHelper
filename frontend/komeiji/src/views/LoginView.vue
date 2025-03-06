<template>
  <div class="login-container">
    <h2>登录</h2>

    <InputBlank ref="usernameRef" :x="50" :y="100" placeholder="用户名" />

    <InputBlank ref="passwordRef" :x="50" :y="150" placeholder="密码" type="password" />

    <button class="login-button" @click="handleLogin">登录</button>

    <label>
      <input type="checkbox" v-model="isRegistering" />
      {{ "注册" }}
    </label>
  </div>
</template>

<script setup>
import { ref } from "vue";
import InputBlank from "@/components/InputBlank.vue";
import userApi from "@/api/userApi.js";
import router from "@/router/index.js";

const usernameRef = ref(null);
const passwordRef = ref(null);
const isRegistering = ref(false);

const handleLogin = async () => {
  const username = usernameRef.valueOf().value.getValue();
  const password = passwordRef.valueOf().value.getValue();

  if (!username || !password) {
    alert("请输入用户名和密码！");
    return;
  }

  if (isRegistering.value) {
    await userApi.register(username, password)
  } else {
    await userApi.login(username, password)
  }

  await router.push("/about");
};
</script>

<style scoped>
.login-container {
  width: 300px;
  top: 50%;
  left: 50%;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: relative;
  transform: translate(-50%, 50%);
}

h2 {
  text-align: center;
}

.login-button {
  width: 100%;
  margin-top: 160px;
  padding: 10px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.login-button:hover {
  background: #0056b3;
}
</style>
