import {getInfo, login, logout} from "@/api/login";
import {getToken, setToken, removeToken, getRefreshToken, setRefreshToken, removeRefreshToken} from "@/utils/auth";
import { ElMessage } from "element-plus";

const state = {
    token: getToken(),
    refreshToken: getRefreshToken(),
    username: '',
    profile: {},
    roles: [],
    permissions: [],
};

const mutations = {
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_REFRESH_TOKEN: (state, refreshToken) => {
        state.refreshToken = refreshToken;
    },
    SET_USERNAME: (state, username) => {
        state.username = username;
    },
    SET_ROLES: (state, roles) => {
        state.roles = roles;
    },
    SET_PERMISSIONS: (state,permissions) =>{
         state.permissions = permissions;
    },
    SET_PROFILE: (state,profile) => {
        state.profile = profile;
    }
};

const actions = {

    //登录
    Login({ commit },userInfo){
        const username = userInfo.username.trim();
        const password = userInfo.password;
        const captcha = userInfo.captcha;
        const rememberMe = userInfo.rememberMe;
        return new Promise<void>((resolve,reject) => {
            login(username,password,captcha,rememberMe).then((res: any) => {
                if(res.errCode == 'GENERAL_EXCEPTION') {
                    ElMessage.error(res.detail);
                    reject();
                    return;
                }
                const token:string = res.data.token;
                const refreshToken:string = res.data.refreshToken;
                setToken(token);
                setRefreshToken(refreshToken)
                commit('SET_TOKEN',token);
                commit('SET_REFRESH_TOKEN',refreshToken);
                resolve();
            }).catch(error => {
                reject(error);
            })
        })
    },

    //登出
    Logout({commit}: { commit: any }){
        return new Promise<void>((resolve, reject) => {
            logout().then((res:any) => {
                this.RemoveAllTokens({commit});
                resolve();
            }).catch(error => {
                reject(error);
            });
        });
    },

    // 设置token
    SetToken({commit},token){
        commit('SET_TOKEN',token);
        setToken(token);
    },

    //移除保存的Token
    RemoveAllTokens({commit}){
        commit('SET_TOKEN','');
        commit('SET_ROLES',[]);
        commit('SET_PERMISSIONS','');
        removeToken();
        removeRefreshToken();
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
    refreshToken: state => state.refreshToken,
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