<script setup>
import { ref, onMounted } from "vue";
import userApi from "@/api/userApi.js";

const users = ref([]);

const fetchUsers = async () => {
  users.value = userApi.getUsers();
};

onMounted(fetchUsers);
</script>

<template>
  <div>
    <h2>用户列表</h2>
    <table border="1">
      <thead>
      <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>邮箱</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.id }}</td>
        <td>{{ user.name }}</td>
        <td>{{ user.userClassCode }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  padding: 8px;
  border: 1px solid black;
  text-align: left;
}
</style>
