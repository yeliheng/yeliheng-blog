import request from '@/utils/request';

export function addDictionary(data: any){
    return request({
        url: '/admin/dictionary',
        method: 'post',
        data: data,
    });
}

export function getDictionary(params:any){
    return request({
        url: '/admin/dictionary',
        method: 'get',
        params: params
    });
}

export function deleteDictionary(id:number){
    return request({
        url: '/admin/dictionary',
        method: 'DELETE',
        params: {
            id: id,
        }
    });
}

export function updateDictionary(data: any){
    return request({
        url: '/admin/dictionary',
        method: 'put',
        data: data,
    });
}

export function refreshDictionaryCache(){
    return request({
        url: '/admin/dictionary/refreshCache',
        method: 'post',
    });
}


