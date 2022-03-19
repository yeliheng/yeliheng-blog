import { createStore } from 'vuex';
import app from './modules/app';
import user from './modules/user';
import permission from './modules/permission';
import tabs from "@/store/modules/tabs";

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
      tabs,
  }
})
