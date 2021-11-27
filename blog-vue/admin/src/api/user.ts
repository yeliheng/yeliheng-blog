import request from '@/utils/request';

export function getUserList(params:any) {
    return request({
        url: '/admin/users',
        method: 'get',
        params: params
    });
}

export function deleteUser(id:number) {
    return request({
        url: '/admin/users',
        method: 'delete',
        params: {'id': id}
    });
}

export function getRoles() {
    return request({
        url: '/admin/roles',
        method: 'get',
    });
}

export function addUser(userData) {
    return request({
        url: '/admin/users',
        method: 'post',
        data: userData,
    });
}

export function updateUser(userData) {
    return request({
        url: '/admin/users',
        method: 'put',
        data: userData,
    });
}
