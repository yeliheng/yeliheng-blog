import request from '@/utils/request';

/**
 * 更新个人资料
 * @param profileData 个人资料信息
 */
export function updateProfile(profileData) {
    return request({
        url: '/users/profile',
        method: 'put',
        data: profileData,
    });
}

/*export function setAvatar(profileData) {
    return request({
        url: '/users/profile/avatar',
        method: 'post',
        data: profileData,
    });
}*/

/**
 * 重置密码
 * @param oldPassword 旧密码
 * @param newPassword 新密码
 */
export function resetPassword(passwordData) {
    return request({
        url: '/users/profile/resetPassword',
        method: 'put',
        params: passwordData,
    });
}
