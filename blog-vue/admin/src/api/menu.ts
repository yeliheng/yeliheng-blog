import request from '@/utils/request';

export const getRouters = () => {
    return request({
        url: '/users/routers',
        method: 'get'
    });
}