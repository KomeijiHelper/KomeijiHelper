import axios from 'axios';
import router from "@/router/index.js";

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
        switch (error.response.status) {
            case 402:
            {
                alert("用户名或密码错误");
                break;
            }
            case 403:
            {
                await router.back();
                break;
            }
            case 456:
            {
                alert("注册失败");
                break;
            }
        }
    }
    return Promise.reject(error);
});

export default {
    async login(username, password) {
        const postJson = {
            userName: username,
            password: password,
        };
        const result = await apiClient.post('/user/login', postJson);
        localStorage.setItem("userName", username)
        return result;
    },

    async register(username, password) {
        const postJson = {
            userName: username,
            password: password,
        };
        const result = await apiClient.post('/user/register', postJson);
        localStorage.setItem("userName", username)
        return result;
    },

    test(){
        return apiClient.get('/user/test');
    },

    async getUsersByUserClass(userClassCode) {
        const postJson = {
            userClassCode: userClassCode,
        };
        return await apiClient.post('/user/getUsersByClass', postJson);
    },

    async checkSession() {
        await apiClient.get('/user/checkSession')
        return true;
    },

    async logout() {
        await apiClient.get('/user/logout');
    }
};