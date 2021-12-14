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
export function getArticleById(id:any) {
    return request({
        url: '/articles/' + id,
        method: 'get',
    });
}