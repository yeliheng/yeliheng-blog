import request from '@/utils/request';

export function getUserList(params:any) {
    return request({
        url: '/admin/users',
        method: 'get',
        params: params
    });
}
