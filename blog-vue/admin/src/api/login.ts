import request from '@/utils/request';

export function login(username,password){
    const data = {
        username,password
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