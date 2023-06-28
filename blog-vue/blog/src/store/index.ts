import { createStore } from 'vuex'

export default createStore({
  state: {
    showSidebar: false,
    siteInfo: {
      'articlesCount': '∞',
      'categoriesCount': '∞',
      'tagsCount': '∞',
      'notice': {
        content: '',
      }
    },
    isDarkMode: true,
  },
  mutations: {
    TOGGLE_SIDEBAR: (state) => {
      state.showSidebar = !state.showSidebar;
    },
    CLOSE_SIDEBAR: (state) => {
      state.showSidebar = false;
    },
    SET_INFO: (state,siteInfo) => {
      state.siteInfo = siteInfo;
    },
    SET_DARK_MODE: (state, isDarkMode) => {
      state.isDarkMode = isDarkMode;
    }
  },
  actions: {
    toggleSidebar({commit}) {
      commit('TOGGLE_SIDEBAR');
    },
    closeSidebar({commit}) {
      commit('CLOSE_SIDEBAR');
    },
    toggleDarkMode({commit, state}) {
        commit('SET_DARK_MODE', !state.isDarkMode);
    }
  },
  modules: {
  }
})
