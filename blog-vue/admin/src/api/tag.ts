import request from '@/utils/request';

export function addTag(tagName: string){
    const data = {
        tagName
    }
    return request({
        url: '/admin/tags',
        method: 'post',
        data: data,
    });
}

export function getTags(params:any){
    return request({
        url: '/tags',
        method: 'get',
        params: params
    });
}

export function deleteTag(id:number){
    return request({
        url: '/admin/tags',
        method: 'DELETE',
        params: {
            id: id,
        }
    });
}

export function updateTag(id: number,tagName: string){
    const data = {
        id,
        tagName,
    }
    return request({
        url: '/admin/tags',
        method: 'put',
        data: data,
    });

}

