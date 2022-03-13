import request from '@/utils/request';

export function addDictionaryData(data: any){
    return request({
        url: '/admin/dictionaryData',
        method: 'post',
        data: data,
    });
}

export function getDictionaryData(params:any){
    return request({
        url: '/admin/dictionaryData',
        method: 'get',
        params: params
    });
}

export function getDictionaryDataByCode(code:string){
    return request({
        url: '/admin/dictionaryDataByCode',
        method: 'get',
        params: {
            dictCode: code
        }
    });
}

export function deleteDictionaryData(id:number){
    return request({
        url: '/admin/dictionaryData',
        method: 'DELETE',
        params: {
            id: id,
        }
    });
}

export function updateDictionaryData(data: any){
    return request({
        url: '/admin/dictionaryData',
        method: 'put',
        data: data,
    });
}


