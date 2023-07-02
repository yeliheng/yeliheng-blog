import { createStore } from 'vuex';
import Cookies from "js-cookie";

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
    isDarkMode: Cookies.get('isDarkMode') === '1',
    loading: false,
  },
  mutations: {
    TOGGLE_SIDEBAR: (state) => {
      state.showSidebar = !state.showSidebar;
    },
    OPEN_SIDEBAR: (state) => {
        state.showSidebar = true;
    },
    CLOSE_SIDEBAR: (state) => {
      state.showSidebar = false;
    },
    SET_INFO: (state,siteInfo) => {
      state.siteInfo = siteInfo;
    },
    TOGGLE_DARK_MODE: (state,isDarkMode) => {
      state.isDarkMode = !isDarkMode;
      Cookies.set('isDarkMode',state.isDarkMode ? '1' : '0');
    },
    IS_LOADING: (state) => {
        state.loading = true;
    },
    NOT_LOADING: (state) => {
        state.loading = false;
    }
  },
  actions: {
    toggleSidebar({commit}) {
      commit('TOGGLE_SIDEBAR');
    },
    openSidebar({commit}) {
      commit('OPEN_SIDEBAR');
      document.body.style.overflow = 'hidden';
    },
    closeSidebar({commit}) {
      commit('CLOSE_SIDEBAR');
      document.body.style.overflow = 'auto';
    },
    toggleDarkMode({commit}) {
      const isDarkMode = Cookies.get('isDarkMode') == '1';
      commit('TOGGLE_DARK_MODE',isDarkMode);
    },
    isLoading({commit}) {
        commit('IS_LOADING');
    },
    notLoading({commit}) {
        commit('NOT_LOADING');
    }
  },
  getters: {
    isDarkMode: state => {
      return Cookies.get('isDarkMode') == '1';
    }
  },
  modules: {
  }
})
