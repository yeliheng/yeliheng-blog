import { getRouters } from "@/api/menu";
import Layout from '@/layout/index.vue';
import NotFound from '@/views/error/404.vue';
import router, { constantRoutes } from "@/router";
   const state = {
        routes: [],
        menuRoutes: [],
        defaultRoutes: [],
    };

    const mutations = {
        SET_ROUTES: (state, routes) => {
            state.menuRoutes = routes;
            state.routes = constantRoutes.concat(routes)
        },

    };

    const actions = {
        GenerateRoutes({ commit }) {
            return new Promise(resolve => {
                getRouters().then(res => {
                    const routerList:any = res.data;

                    routerList.forEach((item) => {

                        //遍历出父菜单，并添加到侧边栏
                        if(item.icon != null){
                            item.icon = "fa " + item.icon + " fa-fw";
                        }
                        //判断是否是父路由
                        if (item.component == "Layout") {
                            item.component = Layout;
                        }else {
                            //若是顶层路由，则直接加载组件
                            item.icon = "fa " + item.icon + " fa-fw";
                            item.component = loadView(item.component);
                            router.addRoute('ParentRoute',item);
                        }

                        if(item.children && item.children.length > 0) {
                            item.children.forEach(route => {
                                //添加子菜单路由
                                route.icon = "fa " + route.icon + " fa-fw";
                                route.component = loadView(route.component);
                                router.addRoute('ParentRoute',route);
                            });

                        }
                    });

                    router.addRoute({
                        path: '/:catchAll(.*)',
                        name: "404",
                        component: NotFound,
                    });

                    commit("SET_ROUTES",routerList);
                    resolve(routerList);
                });
            })
        }
    };


    const getters = {
        
    };
export const loadView = (view: string) => { // 路由懒加载
    return () => import(`@/views/${view}`);
}  

export default {
    namespace: true,
    state,
    mutations,
    actions,
    getters,
}