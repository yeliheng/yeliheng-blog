import request from '@/utils/request';

export function addArticle(title:string, content:string, tagIds: number[]){
    const data = {
        title,
        content,
        tagIds,
    };

    return request({
        url: '/articles',
        method: 'post',
        data: data
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
