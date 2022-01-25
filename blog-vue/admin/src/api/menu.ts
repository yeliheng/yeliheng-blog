import request from '@/utils/request';
import {IMenu} from "@/interface/IMenu";

export const getRouters = () => {
    return request({
        url: '/admin/users/routers',
        method: 'get'
    });
}

/**
 * 获取菜单列表
 */
export function getMenuList(params: any) {
    return request({
        url: '/admin/menus',
        method: 'get',
        params: params,
    });
}

/**
 * 新增菜单
 */
export function addMenu(menu: IMenu) {
    return request({
        url: '/admin/menus',
        method: 'post',
        data: menu,
    })
}

/**
 * 修改菜单
 */
export function updateMenu(menu: IMenu) {
    return request({
        url: '/admin/menus',
        method: 'put',
        data: menu,
    })
}

/**
 * 删除菜单
 */
export function deleteMenu(id: number) {
    return request({
        url: '/admin/menus',
        method: 'delete',
        params: {'id': id},
    });
}