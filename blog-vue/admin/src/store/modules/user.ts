import { login } from "@/api/login"
import { getToken,setToken,removeToken } from "@/utils/auth"
const state = {
    
}

const mutations = {
    SET_TOKEN: (state, token) => {
        state.token = token
    },
}

const actions = {
    Login({ commit },userInfo){
        const username = userInfo.username.trim();
        const password = userInfo.password;
        return new Promise<void>((resolve,reject) => {
            login(username,password).then((res: any) => {
                console.log(res);
               // setToken(res.token);
                //commit('SET_TOKEN',res.token);
                resolve();
            }).catch(error => {
                reject(error);
            })
        })
    }
}

export default {
    namespace: true,
    state,
    mutations,
    actions,
}