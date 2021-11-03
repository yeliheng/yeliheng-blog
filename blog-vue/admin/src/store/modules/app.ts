import Cookies from "js-cookie";

const state = {
    sidebarCollapse: Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true,
    isMobile :Cookies.get('isMobile') ?!!+Cookies.get('isMobile') : false,
    sidebarClosed: Cookies.get('sidebarClosed') ? !!+Cookies.get('sidebarClosed') : false,
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
    UNCOLLAPSE_SIDEBAR: (state) => {
        state.sidebarCollapse = false;
        Cookies.set('sidebarStatus','0');
    },
    CLOSE_SIDEBAR: (state) => {
        Cookies.set('sidebarClosed','1');
        state.sidebarClosed = true;
    },
    OPEN_SIDEBAR: (state) => {
        Cookies.set('sidebarClosed','0');
        state.sidebarClosed = false;
    },
    //切换到移动端
    TOGGLE_MOBILE: (state) => {
        state.isMobile = !state.isMobile;
        if(state.isMobile){
            Cookies.set('isMobile','0');
            state.sidebarClosed = true;
        }else{
            Cookies.set('isMobile','1');
            state.sidebarClosed = false;
        }
    },
}

const actions = {
    toggleSidebar({commit}) {
        commit('TOGGLE_SIDEBAR');
    },
    closeSidebar({commit}) {
        commit('CLOSE_SIDEBAR');
    },
    openSidebar({commit}){
        commit('OPEN_SIDEBAR');  
        commit('UNCOLLAPSE_SIDEBAR');
    },
    toggleMobile({commit}) {
        commit('TOGGLE_MOBILE');
    }
}

const getters = {
    sidebarCollapse: state => state.app.sidebarCollapse,
    isMobile: state => state.app.isMobile,
    sidebarClosed: state => state.app.sidebarClosed,
}

export default {
    namespace: true,
    state,
    mutations,
    actions,
    getters,
}