<script setup>
import userApi from '@/api/userApi';
import {onMounted, ref} from "vue"; // 假设你有一个 userApi.js 文件

// 定义一个方法来处理点击事件
const handleButtonClick = async () => {
  try {
    const response = await userApi.getUsersByUserClass(0); // 调用 userApi.test 方法
    console.log(response);
  } catch (error) {
    console.error('Error calling test API:', error); // 处理错误
  }
};

const userId = ref('')
onMounted(async () => {
  userId.value = await userApi.getUserName()
});

</script>

<template>
  <div>
    <h1>About {{userId}}</h1>
    <button @click="handleButtonClick">Call API</button> <!-- 添加按钮并绑定点击事件 -->
  </div>
</template>

<style scoped>
/* 这里可以添加样式 */
button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
