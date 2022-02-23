export interface IArticle{
    categoryId: number,
    title: string,
    summary: string,
    content: string,
    tagIds: number[],
    top: number,
    visible: number,
}