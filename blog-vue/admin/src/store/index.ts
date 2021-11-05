import { createStore } from 'vuex';
import app from './modules/app';
import user from './modules/user';
import permission from './modules/permission';

export default createStore({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
      app,
      user,
      permission,
  }
})
