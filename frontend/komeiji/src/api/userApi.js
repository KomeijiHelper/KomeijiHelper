import axios from 'axios';
import router from "@/router/index.js";

// 创建 axios 实例
const apiClient = axios.create({
    baseURL: 'http://127.0.0.1:8081', // 基础 URL
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: true,
});

apiClient.interceptors.response.use((response) => {
    return response;
}, async (error) => {
    if (error.response) {
        if (error.response.status === 401) {
            alert('会话失效，跳转到登录页');
            localStorage.removeItem('session');
            await router.push('/login');
        }
        else if (error.response.status === 402) {
            alert("用户名或密码错误");
        }
        else if (error.response.status === 403) {
            await router.back();
        }
    }
    return Promise.reject(error);
});

export default {
    async login(username, password) {
        const postJson = {
            uname: username,
            password: password,
        };
        return apiClient.post('/user/login', postJson);
    },

    register(username, password) {
        const postJson = {
            uname: username,
            password: password,
        };
        return apiClient.post('/user/register', postJson);
    },

    getUserName(){
        return apiClient.get('/user/getUserName');
    },

    test(){
        const testres = apiClient.get('/user/test');
        testres.then((response) => {console.log(response)})
        return testres;
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