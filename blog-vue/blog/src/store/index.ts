import { createStore } from 'vuex'

export default createStore({
  state: {
    categories: {},
    showSidebar: false,
  },
  mutations: {
    SET_CATEGORIES: (state, categories) => {
      state.categories = categories;
    },
    TOGGLE_SIDEBAR: (state) => {
      state.showSidebar = !state.showSidebar;
    },
    CLOSE_SIDEBAR: (state) => {
      state.showSidebar = false;
    },
  },
  actions: {
    toggleSidebar({commit}) {
      commit('TOGGLE_SIDEBAR');
    },
    closeSidebar({commit}) {
      commit('CLOSE_SIDEBAR');
    }
  },
  modules: {
  }
})
