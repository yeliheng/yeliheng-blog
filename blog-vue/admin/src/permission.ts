import NProgress from "nprogress";
import 'nprogress/nprogress.css';
import router from "./router";
import { getToken } from '@/utils/auth';
import store from "./store";
import { ElMessage } from "element-plus";

NProgress.configure({showSpinner: false});

const whiteList = ['/login']
router.beforeEach((to,from, next) =>{
    NProgress.start();
    if(getToken()){
        //存在Token
       if(to.path === '/login'){
            NProgress.done();
            next({
                path: '/'
            });

       }else{

           if(store.getters.roles.length === 0){ 
            //获取用户信息
            store.dispatch('GetUserInfo').then(() => {
                //获取路由信息
                store.dispatch('GenerateRoutes').then(routes => {
                    //router.addRoute(routes);
                    next({...to,replace:true});
                })
            }).catch(error => {
                store.dispatch('Logout').then(() => {
                    ElMessage({
                        type: 'error',
                        message: "用户凭据已过期，请重新登录!",
                    });
                    router.push('/login');
                });
            });

           } else {
                next();
           }
       }
    }else{
        //不存在Token
        if (whiteList.indexOf(to.path) !== -1) {
            next();
        }else{
            NProgress.done();
            next({
                path: '/login'
            });
        }
    }
});

router.afterEach(() => {
    NProgress.done()
})