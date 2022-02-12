import request from '@/utils/request';

export function getOperateLogs(params:any){
    return request({
        url: '/admin/operateLogs',
        method: 'get',
        params: params
    });
}

export function deleteOperateLogs(ids: any){
    return request({
        url: '/admin/operateLogs',
        method: 'delete',
        params: {
          "operateLogIds": ids
        },
    });
}

export function clearOperateLogs(){
    return request({
        url: '/admin/operateLogs/clear',
        method: 'delete',
    });
}



