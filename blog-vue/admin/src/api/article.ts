import request from '@/utils/request';
import { IArticle } from '@/interface/IArticle';

export function addArticle(article: IArticle){

    return request({
        url: '/articles',
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
