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