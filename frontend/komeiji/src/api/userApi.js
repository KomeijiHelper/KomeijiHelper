import axios from 'axios';
import router from "@/router/index.js";

// 创建 axios 实例
const apiClient = axios.create({
    baseURL: 'http://127.0.0.1:8081', // 基础 URL
    headers: {
        'Content-Type': 'application/json',
    },
});

apiClient.interceptors.response.use((response) => {
    return response;
}, async (error) => {
    if (error.response) {
        if (error.response.status === 401) {
            console.log('Session 失效，跳转到登录页');
            localStorage.removeItem('session'); // 清除 session
            await router.push('/login'); // 跳转到登录页
        } else if (error.response.status === 403) {
            console.log('无权限访问');
        }
    }
    return Promise.reject(error);
});

export default {
    login(username, password) {
        const postJson = {
            uname: username,
            password: password,
        };
        const result = apiClient.post('/user/login', postJson, {withCredentials: true});
        result.then((response) => {console.log(response)})
        return result;
    },

    register(username, password) {
        const postJson = {
            uname: username,
            password: password,
        };
        const result = apiClient.post('/user/register', postJson);
        result.then((response) => {console.log(response)})
        return result;
    },

    getUserName(){
        return apiClient.get('/user/getUserName', {withCredentials: true});
    },

    test(){
        const testres = apiClient.get('/user/test', {withCredentials: true});
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