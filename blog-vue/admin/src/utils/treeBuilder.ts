
/**
 * 深克隆构建树状数据
 * @param source 源数据
 * @param idFieldName id字段名 例如字符串: id
 * @param parentIdFieldName parentId字段名 例如: parentId
 * @param childrenFieldName children字段名 例如: children
 */
export const buildMenuTree = (source: any, idFieldName: string, parentIdFieldName: string, childrenFieldName: string) => {
    const cloneData = JSON.parse(JSON.stringify(source));
    return cloneData.filter(father=>{
        const branchArr = cloneData.filter(child => father[idFieldName] == child[parentIdFieldName]);
        branchArr.length>0 ? father[childrenFieldName] = branchArr : '';
        return father[parentIdFieldName] == 0;
    });
}