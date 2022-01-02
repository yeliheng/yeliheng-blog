import request from '@/utils/request';

/**
 * 获取首页文章列表
 */
 export function getArticleList(params:any) {
    return request({
        url: '/articles',
        method: 'get',
        params: params
    });
}


//通过Id获取文章
export function getArticleByUrl(url: any) {
    return request({
        url: '/articles/' + url,
        method: 'get',
    });
}

//获取分类列表
export function getCategoryList() {
    return request({
        url: '/categories',
        method: 'get',
    });
}

//获取标签列表
export function getTagList() {
    return request({
        url: '/tags',
        method: 'get',
    });
}

//根据分类id获取文章
export function getArticleListByCategoryId(params:any) {
    return request({
        url: '/articles/category',
        method: 'get',
        params: params
    });
}

//根据标签id获取文章
export function getArticleListByTagId(params:any) {
    return request({
        url: '/articles/tag',
        method: 'get',
        params: params
    });
}

//获取站点信息
export function getSiteInfo() {
    return request({
        url: '/site/info',
        method: 'get'
    });
}