import request from '@/utils/request';

export function addCategory(categoryName: string){
    const data = {
        categoryName
    }
    return request({
        url: '/categories',
        method: 'post',
        data: data,
    });
}

export function getCategories(params:any){
    return request({
        url: '/categories',
        method: 'get',
        params: params
    });
}

export function deleteCategory(id:number){
    return request({
        url: '/categories',
        method: 'DELETE',
        params: {
            id: id,
        }
    });
}

export function updateCategory(id: number,categoryName: string){
    const data = {
        id,
        categoryName,
    }
    return request({
        url: '/categories',
        method: 'put',
        data: data,
    });

}

