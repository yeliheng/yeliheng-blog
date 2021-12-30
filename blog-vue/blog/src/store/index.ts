import { createStore } from 'vuex'

export default createStore({
  state: {
    categories: {},
  },
  mutations: {
    SET_CATEGORIES: (state, categories) => {
      state.categories = categories;
    }
  },
  actions: {
  },
  modules: {
  }
})
