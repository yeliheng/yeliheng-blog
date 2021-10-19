import axios, { Axios, AxiosResponse } from "axios";
import { getToken } from "@/utils/auth";
import errorCode from "@/utils/errorCode";
import { ElMessage, ElNotification } from "element-plus";

const requestHeaders: HeadersInit = new Headers();
requestHeaders.set('Content-Type','application/json;charset=utf-8');
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 10000
});

//request拦截器
service.interceptors.request.use(config => {
    config.headers!['Autorization'] = 'Bearer ' + getToken();
    if(config.method === 'get' && config.params){
        let url = config.url + '?';
        for (const propName of Object.keys(config.params)) {
            const value = config.params[propName];
            const part = encodeURIComponent(propName) + "=";
            if (value !== null && typeof(value) !== "undefined") {
              if (typeof value === 'object') {
                for (const key of Object.keys(value)) {
                  const params = propName + '[' + key + ']';
                  const subPart = encodeURIComponent(params) + "=";
                  url += subPart + encodeURIComponent(value[key]) + "&";
                }
              } else {
                url += part + encodeURIComponent(value) + "&";
              }
            }
          }
        url = url.slice(0, -1);
        config.params = {};
        config.url = url;
    }
    return config;
},error => {
    console.log(error);
    Promise.reject(error);
});

//响应拦截器
service.interceptors.response.use(res=> {
    const response: AxiosResponse<any,any> = res;
    const code = response.data.httpCode || 200;
    const msg = errorCode[code]|| response.data.message || errorCode['default'];
    if(code === 500){
      ElMessage({
        message: msg,
        type: 'error'
      });
      return Promise.reject(new Error(msg));
    }else if(code != 200){
      ElNotification({
        title: msg,
        type: 'error'
      });
      return Promise.reject('error');
    }else{
      return res.data;
    }
},
error => {
  console.log('error: '+ error);
  let { message } = error;
  if (message == "Network Error") {
    message = "连接服务器失败!";
  }
  ElMessage({
    message: message,
    type: 'error',
    duration: 5 * 1000
  });
  return Promise.reject(error);
}
)

export default service;
