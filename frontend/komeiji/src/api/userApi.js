import axios from 'axios';

// 创建 axios 实例
const apiClient = axios.create({
    baseURL: 'http://127.0.0.1:8081', // 基础 URL
    headers: {
        'Content-Type': 'application/json',
    },
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