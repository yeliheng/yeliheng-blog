import Cookies from "js-cookie";

const state = {
    sidebarCollapse: Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true,
}

const mutations = {
    TOGGLE_SIDEBAR: (state) => {
        state.sidebarCollapse = !state.sidebarCollapse;
        if(state.sidebarCollapse){
            Cookies.set('sidebarStatus','1');
        }else{
            Cookies.set('sidebarStatus','0');
        }
    },
    CLOSE_SIDEBAR: (state) => {
        Cookies.set('sidebarStatus','0');
        state.sidebarCollapse = false;
    }
}

const actions = {
    toggleSidebar({commit}) {
        commit('TOGGLE_SIDEBAR');
    },
    closeSidebar({commit}) {
        commit('CLOSE_SIDEBAR');
    }
}

const getters = {
    sidebarCollapse: state => state.app.sidebarCollapse,
}

export default {
    namespace: true,
    state,
    mutations,
    actions,
    getters,
}