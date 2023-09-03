package com.yeliheng.blogsystem.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Schema(description = "文章")
public class Article {
    @Excel(name = "文章ID")
    private Long id;

    private Long userId;

    private Long categoryId;

    private Long draftId;

    @Excel(name = "文章标题",width = 30)
    @NotBlank(message = "标题不能为空")
    private String title;

    @Excel(name = "文章简介",width = 30)
    private String summary;

    @Excel(name = "文章内容",width = 35)
    @NotBlank(message = "内容不能为空")
    private String content;

    @Excel(name = "持久化链接",width = 30)
    private String url;

    private String top;

    @Excel(name = "可见性", replace = {"可见_1","私密_0"})
    private String visible;

    @Excel(name = "字数")
    private int words;

    @Excel(name = "阅读量")
    private int views;

    @Excel(name = "预估阅读时间")
    private int readingTime;

    private Long[] tagIds;

    @ExcelEntity(name = "分类")
    private Category category;

    @ExcelCollection(name = "标签")
    private List<Tag> tags;

    @Excel(name = "发布时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Excel(name = "更新时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getDraftId() {
        return draftId;
    }

    public void setDraftId(Long draftId) {
        this.draftId = draftId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(int readingTime) {
        this.readingTime = readingTime;
    }

    public Long[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
