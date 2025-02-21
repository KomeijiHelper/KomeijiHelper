// store/modules/user.js
const state = {
    userId: null
}

const mutations = {
    SET_USER_ID(state, userId) {
        state.userId = userId;
    }
}

const actions = {
    setUserId({ commit }, userId) {
        commit('SET_USER_ID', userId);
        localStorage.setItem('userId', userId); // 持久化到 localStorage
    },
    clearUserId({ commit }) {
        commit('SET_USER_ID', null);
        localStorage.removeItem('userId');
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
};