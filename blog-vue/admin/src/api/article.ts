import request from '@/utils/request';
import { IArticle } from '@/interface/IArticle';

export function addArticle(article: IArticle){

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

export function getTags(){
    return request({
        url: '/tags',
        method: 'get',
    });
}

export function getArticlesAdmin(params:any){
    return request({
        url: '/admin/articles/list',
        method: 'get',
        params: params
    });
}

export function deleteArticle(id:number){
    return request({
        url: '/admin/articles',
        method: 'delete',
        params: {'id': id}
    });
}