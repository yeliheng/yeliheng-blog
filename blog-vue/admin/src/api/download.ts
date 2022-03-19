import request from '@/utils/request';
export function download(path:string) {
    return request({
        url: '/admin/download',
        method: 'get',
        params: {'path': path},
        responseType: 'blob',
    });
}