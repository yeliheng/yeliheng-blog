package com.yeliheng.blogsystem.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;


@Data
public class Article {
    @Excel(name = "文章ID")
    private Long id;

    private Long userId;

    private Long categoryId;

    private Long draftId;

    @Excel(name = "文章标题",width = 30)
    private String title;

    @Excel(name = "文章简介",width = 30)
    private String summary;

    @Excel(name = "文章内容",width = 35)
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

}
