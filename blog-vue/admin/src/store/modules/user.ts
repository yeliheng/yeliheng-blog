import { getInfo, login, logout } from "@/api/login";
import { getToken,setToken,removeToken } from "@/utils/auth";
import { ElMessage } from "element-plus";

const state = {
    token: getToken(),
    username: '',
    roles: [],
    permissions: [],
};

const mutations = {
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_USERNAME: (state, username) => {
        state.username = username;
    },
    SET_ROLES: (state, roles) => {
        state.roles = roles;
    },
    SET_PERMISSIONS: (state,permissions) =>{
         state.permissions = permissions;
    }
};

const actions = {

    //登录
    Login({ commit },userInfo){
        const username = userInfo.username.trim();
        const password = userInfo.password;
        const rememberMe = userInfo.rememberMe;
        return new Promise<void>((resolve,reject) => {
            login(username,password,rememberMe).then((res: any) => {
                if(res.errCode == 'GENERAL_EXCEPTION') {
                    ElMessage.error(res.detail);
                    reject();
                    return;
                }
                const token:string = res.data.token;
                setToken(token);
                commit('SET_TOKEN',token);
                resolve();
            }).catch(error => {
                reject(error);
            })
        })
    },

    //登出
    Logout({commit}){
        return new Promise<void>((resolve, reject) => {
            logout().then((res:any) => {
                commit('SET_TOKEN','');
                commit('SET_ROLES',[]);
                commit('SET_PERMISSIONS','');
                removeToken();
                resolve();
            }).catch(error => {
                reject(error);
            });
        });
    },

    //获取用户信息
    GetUserInfo({commit}){
        return new Promise((resolve, reject) => {
            getInfo().then((res:any) => {
                const user = res.data.user;
                if(res.data.roles && res.data.roles.length > 0){
                    commit('SET_ROLES',res.data.roles);
                    commit('SET_PERMISSIONS',res.data.permissions);
                }else{
                    commit('SET_ROLES',['ROLE_NONE']);//无角色
                }
                commit('SET_USERNAME',user.username);
                resolve(res);
            }).catch(error => {
                reject(error);
            });
        });
    }

};

const getters = {
    token: state => state.token,
    username: state => state.username,  
    roles: state => state.roles,
    permissions: state => state.permissions,
};

export default {
    namespace: true,
    state,
    mutations,
    actions,
    getters,
};