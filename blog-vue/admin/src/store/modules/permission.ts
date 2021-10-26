import { getRouters } from "@/api/menu";
import { constantRoutes } from "@/router";

   const state = {
        routes: [],
        addRoutes: [],
        defaultRoutes: [],
    };

    const mutations = {
        SET_ROUTES: (state, routes) => {
            state.addRoutes = routes;
            state.routes = constantRoutes.concat(routes)
        },
        SET_DEFAULT_ROUTES: (state, routes) => {
            state.defaultRoutes = constantRoutes.concat(routes)
        },

    };

    const actions = {
        GenerateRoutes({ commit }) {
            return new Promise(resolve => {
                getRouters().then(res => {
                    //console.log(res);
                    const routers = JSON.parse(JSON.stringify(res.data));
                    console.log(routers);
                });
            })
        }
    };

    const getters = {
        
    };

export default {
    namespace: true,
    state,
    mutations,
    actions,
    getters,
}