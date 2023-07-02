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
    closeSidebar({commit}) {
      commit('CLOSE_SIDEBAR');
    },
    toggleDarkMode({commit,getters}) {
      commit('TOGGLE_DARK_MODE',getters.isDarkMode);
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
      if(Cookies.get('isDarkMode') == '1'){
        return true;
      }else{
        return false;
      }
    }
  },
  modules: {
  }
})
