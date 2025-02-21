import {Store} from 'vuex';
import user from './modules/user.js';

export default new Store({
    modules: {
        user,
    }
});