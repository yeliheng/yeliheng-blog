package com.yeliheng.blogsystem.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@ApiModel(description = "文章")
public class Article {
    @Id
    private Long id;

    private Long user_id;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String summary;

    @NotBlank(message = "内容不能为空")
    private String content;

    private String url;

    private Integer vivible;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
