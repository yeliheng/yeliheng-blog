import request from '@/utils/request';

export function getRoleList(params:any) {
    return request({
        url: '/admin/roles',
        method: 'get',
        params: params
    });
}

export function deleteRole(id:number) {
    return request({
        url: '/admin/roles',
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

export function updateRole(roleData) {
    return request({
        url: '/admin/roles',
        method: 'put',
        data: roleData,
    });
}

export function getSelectMenuTree() { 
    return request({
        url: '/admin/menus/tree',
        method: 'get',
    });
}

export function getMenuIdsByRoleId(id) {
    return request({
        url: '/admin/menus/getByRoleId',
        method: 'get',
        params: {'id': id}
    });
}
