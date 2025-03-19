<script setup>
import userApi from '@/api/userApi';

// 定义一个方法来处理点击事件
const handleButtonClick = async () => {
  try {
    const response = await userApi.getUsersByUserClass(1); // 调用 userApi.test 方法
    console.log(response.data.data);
  } catch (error) {
    console.error('Error calling test API:', error); // 处理错误
  }
};

const handleButtonClick1 = async () => {
  try {
    const response = await userApi.test();
    console.log(response);
  } catch (error) {
    console.error('Error calling test API:', error); // 处理错误
  }
};



</script>

<template>
  <div>
    <h1>About</h1>
    <button @click="handleButtonClick">Call API</button>
    <button @click="handleButtonClick1">Call Test</button>
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
