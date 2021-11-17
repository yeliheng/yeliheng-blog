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
    config.headers!['Authorization'] = 'Bearer ' + getToken();
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
service.interceptors.response.use((res: any)=> {
    if(res.status == 200){
      return res.data || {};
    }else{
      Promise.reject();
    }

},
error => {

  if(error.message === "Network Error"){
    showErrorMessage("服务器开小差了...");
    return;
  }

  //console.log(error.response);
  const status = error.response.status;
  switch(status) {
    case 401:
      showErrorMessage("用户名或密码错误");
    break;
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
