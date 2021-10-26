import NProgress from "nprogress";
import 'nprogress/nprogress.css';
import router from "./router";
import { getToken } from '@/utils/auth';
import store from "./store";
import { ElMessage } from "element-plus";

NProgress.configure({showSpinner: false});

const whiteList = ['/login']

router.beforeEach((to,from) =>{
    NProgress.start();
    console.log(getToken());
    if(getToken()){
       if(to.path === '/login'){
            NProgress.done();
            return{
                path: '/'
            };

       }else{
           if(store.getters.roles.length === 0){ 
            //获取用户信息
            store.dispatch('GetUserInfo').then(() => {
                //获取路由信息
                store.dispatch('GenerateRoutes').then(routes => {
                    router.addRoute(routes);
                    return;
                })
            }).catch(error => {
                store.dispatch('LogOut').then(() => {
                    ElMessage({
                        type: 'error',
                        message: error
                    });
                    return ({ path: '/' });
                });
            });

           } else {
                return;
           }
       }
    }else{
        console.log("不存在Token");
        if (whiteList.indexOf(to.path) !== -1) {
            return;
        }else{
            NProgress.done();
            return{
                path: '/login'
            };
        }
    }
});

router.afterEach(() => {
    NProgress.done()
})