package com.yeliheng.blogsystem.domain;

public class SiteInfo {
    private Integer articlesCount;

    private Integer categoriesCount;

    private Integer tagsCount;

    private Notice notice;

    public Integer getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(Integer articlesCount) {
        this.articlesCount = articlesCount;
    }

    public Integer getCategoriesCount() {
        return categoriesCount;
    }

    public void setCategoriesCount(Integer categoriesCount) {
        this.categoriesCount = categoriesCount;
    }

    public Integer getTagsCount() {
        return tagsCount;
    }

    public void setTagsCount(Integer tagsCount) {
        this.tagsCount = tagsCount;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
