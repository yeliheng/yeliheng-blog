import request from '@/utils/request';

export function getRoleList(params:any) {
    return request({
        url: '/admin/roles',
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

export function addRole(roleData) {
    return request({
        url: '/admin/roles',
        method: 'post',
        data: roleData,
    });
}

export function updateUser(userData) {
    return request({
        url: '/admin/users',
        method: 'put',
        data: userData,
    });
}
