import Cookies from 'js-cookie'

export function getToken(){
    return Cookies.get('User-Token');
}

export function getRefreshToken(){
    return Cookies.get('Refresh-Token');
}

export function setToken(token: string){
    return Cookies.set('User-Token',token);
}

export function setRefreshToken(token: string){
    return Cookies.set('Refresh-Token',token);
}

export function removeRefreshToken(){
    return Cookies.remove('Refresh-Token');
}

export function removeToken(){
    return Cookies.remove('User-Token');
}