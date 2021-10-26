import { getInfo, login } from "@/api/login";
import { getToken,setToken,removeToken } from "@/utils/auth";

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
        return new Promise<void>((resolve,reject) => {
            login(username,password).then((res: any) => {
                console.log(res);
                const token:string = res.data.token;
                setToken(token);
                commit('SET_TOKEN',token);
                resolve();
            }).catch(error => {
                reject(error);
            })
        })
    },

    //获取用户信息
    GetUserInfo({commit, state}){

        return new Promise((resolve, reject) => {
            getInfo().then((res:any) => {
                const user = res.data.user;
                if(res.data.roles && res.data.roles.length > 0){
                    console.log('commit');
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