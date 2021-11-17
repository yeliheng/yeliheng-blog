import request from '@/utils/request';

export function addCategory(categoryName: string){
    const data = {
        categoryName
    }
    return request({
        url: '/admin/categories',
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
        url: '/admin/categories',
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
        url: '/admin/categories',
        method: 'put',
        data: data,
    });

}

