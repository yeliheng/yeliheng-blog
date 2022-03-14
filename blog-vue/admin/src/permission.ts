import NProgress from "nprogress";
import 'nprogress/nprogress.css';
import router from "./router";
import { getToken } from '@/utils/auth';
import store from "./store";
import { ElMessage } from "element-plus";

NProgress.configure({showSpinner: false});

const whiteList = ['/login']
router.beforeEach((to,from, next) =>{
    if(to.meta.title) {
        document.title = to.meta.title as string + " - YNetwork后台管理系统";
    }
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
            store.dispatch('GetUserInfo').then((res: any) => {
                store.commit("SET_PROFILE",res.data.user);
                //获取路由信息
                store.dispatch('GenerateRoutes').then(routes => {
                    next({...to,replace:true});
                })
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