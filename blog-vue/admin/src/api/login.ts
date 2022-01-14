import request from '@/utils/request';

export function login(username,password,rememberMe){
    const data = {
        username,password,rememberMe
    };

    return request({
        url: '/login',
        method: 'post',
        data: data
    });
}

export function getInfo() {
    return request({
        url: '/admin/users/info',
        method: 'get',
    });
}

export function logout() {
    return request({
        url: '/logout',
        method: 'post',
    });
}