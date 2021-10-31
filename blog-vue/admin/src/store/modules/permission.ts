import { getRouters } from "@/api/menu";
import Layout from '@/layout/index.vue';
import router, { constantRoutes } from "@/router";

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
                    const routerList:any = res.data;
                    routerList.forEach((item) => {
                        //添加父菜单路由
                        if(item.icon != null){
                            item.icon = "fa " + item.icon + " fa-fw";
                        }
                        if (item.component == "Layout") {
                            item.component = Layout;
                          }
                        router.addRoute(item);
                        if(item.children && item.children.length > 0) {
                            item.children.forEach(route => {
                                //添加子菜单路由
                                route.icon = "fa " + route.icon + " fa-fw";
                                router.addRoute(route);
                                route.component = loadView(route.component);
                            });
                        }
                    });
                    commit("SET_ROUTES",routerList);
                    
                });
            })
        }
    };


    const getters = {
        
    };

export const loadView = view => {
    // 路由懒加载
    return resolve => require([`@/views${view}`], resolve);
};  

export default {
    namespace: true,
    state,
    mutations,
    actions,
    getters,
}