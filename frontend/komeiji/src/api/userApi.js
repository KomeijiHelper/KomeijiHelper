import axios from 'axios';

// 创建 axios 实例
const apiClient = axios.create({
    baseURL: 'https://api.example.com', // 基础 URL
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    login(username, password) {
        return apiClient.post('login', username, password);
    },

    register(username, password) {
        return apiClient.post('register', username, password);
    },

    getUsers() {
        return apiClient.get('/users');
    },

    // 获取单个用户
    getUser(id) {
        return apiClient.get(`/users/${id}`);
    },

    // 创建用户
    createUser(userData) {
        return apiClient.post('/users', userData);
    },

    // 更新用户
    updateUser(id, userData) {
        return apiClient.put(`/users/${id}`, userData);
    },

    // 删除用户
    deleteUser(id) {
        return apiClient.delete(`/users/${id}`);
    },
};