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
        url: '/users/info',
        method: 'get',
    });
}