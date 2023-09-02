import {IDraft} from "@/interface/IDraft";
import request from "@/utils/request";

// 保存草稿
export function addDraft(draft: IDraft) {
    return request({
        url: '/admin/draft',
        method: 'post',
        data: draft
    });
}

// 删除草稿
export function deleteDraft(id: number) {
    return request({
        url: '/admin/draft',
        method: 'delete',
        params: {'id': id}
    });
}

// 更新草稿
export function updateDraft(draft: IDraft) {
    return request({
        url: '/admin/draft',
        method: 'put',
        data: draft
    });
}

// 获取草稿
export function getDraft(id: number) {
    return request({
        url: '/admin/draft/' + id,
        method: 'get',
    });
}

// 获取草稿列表
export function getDraftList(params: any) {
    return request({
        url: '/admin/draft/list',
        method: 'get',
        params: params
    });
}