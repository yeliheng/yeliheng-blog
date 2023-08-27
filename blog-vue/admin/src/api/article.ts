import request from '@/utils/request';
import { IArticle } from '@/interface/IArticle';

export function addArticle(article: IArticle) {

    return request({
        url: '/admin/articles',
        method: 'post',
        data: article
    });
}

export function getCategories(){
    return request({
        url: '/categories',
        method: 'get',
    });
}

export function getTags() {
    return request({
        url: '/tags',
        method: 'get',
    });
}

export function getArticlesAdmin(params:any) {
    return request({
        url: '/admin/articles/list',
        method: 'get',
        params: params
    });
}

export function deleteArticle(id:number) {
    return request({
        url: '/admin/articles',
        method: 'delete',
        params: {'id': id}
    });
}

//通过Id获取后台文章
export function getArticleByIdBacked(id:any) {
    return request({
        url: '/admin/articles/' + id,
        method: 'get',
    });
}

//更新文章
export function updateArticle(article) {
    return request({
        url: '/admin/articles',
        method: 'put',
        data: article
    });
}

//导出文章
export function exportArticles() {
    return request({
        url: '/admin/articles/export',
        method: 'get',
    });
}

// 上传图片 通过formData
export function uploadImage(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return request({
        url: '/admin/articles/uploadImage',
        method: 'post',
        data: formData
    });
}