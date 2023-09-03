import axios from "axios";
import {getRefreshToken, getToken} from "@/utils/auth";
import { ElMessage } from "element-plus";
import store from "@/store";
import router from "@/router";
import {refreshToken} from "@/api/login";
axios.defaults.withCredentials = true
const requestHeaders: HeadersInit = new Headers();
requestHeaders.set('Content-Type','application/json;charset=utf-8');
let isRefreshing = false; // 同时只能有一个刷新token的请求
let unauthorizedRequests: any[] = []; // 过期的请求

export const addRequest = (request) => {
    unauthorizedRequests.push(request);
}

export const retryRequests = () => {
    unauthorizedRequests.forEach((cb: any) => cb());
    unauthorizedRequests = [];
}

export const refreshTokenRequest = () => {
    if(isRefreshing) {
        return;
    }
    isRefreshing = true;
    refreshToken(getRefreshToken()).then((res: any) => {
        if(res.errCode == 'GENERAL_EXCEPTION') {
            ElMessage.error("用户凭据已过期，请重新登录!");
            store.dispatch('RemoveAllTokens');
            router.push('/login');
            isRefreshing = false;
            return;
        }
        const token:string = res.data.token;
        store.dispatch('SetToken',token);
        // 刷新完token后，重新发送请求
        retryRequests();
    }).catch(error => {
        ElMessage.error("用户凭据已过期，请重新登录!");
        store.dispatch('RemoveAllTokens');
        router.push('/login');
        isRefreshing = false;
    });
}

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 10000
});

axios.defaults.withCredentials = true;

//request拦截器
service.interceptors.request.use(config => {
    config.headers!['Authorization'] = 'Bearer ' + getToken();
    return config;
},error => {
    console.log(error);
    Promise.reject(error);
});
//响应拦截器
service.interceptors.response.use(  (res: any)=> {
    if(res.status == 200){
      return res.data || {};
    }else{
        Promise.reject();
    }

},
async error => {

  if(error.message === "Network Error"){
    showErrorMessage("服务器开小差了...");
    return;
  }

  const status = error.response.status;
  switch(status) {
    case 500: 
      showErrorMessage("服务器内部错误");
      break;
    case 400:
      showErrorMessage("输入的数据不合法");
      break;
    case 403:
      showErrorMessage("权限不足");
      break;
    case 404:
      showErrorMessage("访问的资源不存在");
      break;
    case 405:
      showErrorMessage("请求方法不支持");
      break;
    case 401:
        // 将当前请求保存到未授权请求数组中
        return new Promise((resolve, reject) => {
            const config = error.response.config;
            config.headers.Authorization = 'Bearer ' + getToken();
            addRequest(() => resolve(service(config)));
            // 刷新token
            refreshTokenRequest();
        });
  }
}
);

function showErrorMessage(message: string ){
  ElMessage({
    message: message,
    type: 'error',
    duration: 5 * 1000
  });
}

export default service;
