const state = {
    tabsList: [],
};
const mutations = {
    delTabsItem(state, data) {
        state.tabsList.splice(data.index, 1);
    },
    setTabsItem(state, data) {
        state.tabsList.push(data)
    },
    clearTabs(state) {
        state.tabsList = []
    },
    closeTabsOther(state, data) {
        state.tabsList = data;
    },
    closeCurrentTab(state, data) {
        for (let i = 0, len = state.tabsList.length; i < len; i++) {
            const item = state.tabsList[i];
            if (item.path === data.$route.fullPath) {
                if (i < len - 1) {
                    data.$router.push(state.tabsList[i + 1].path);
                } else if (i > 0) {
                    data.$router.push(state.tabsList[i - 1].path);
                } else {
                    data.$router.push("/");
                }
                state.tabsList.splice(i, 1);
                break;
            }
        }
    },
};
export default {
    namespace: true,
    state,
    mutations,
};