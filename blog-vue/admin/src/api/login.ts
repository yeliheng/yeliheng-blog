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