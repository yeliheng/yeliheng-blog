package com.yeliheng.blogsystem.vo;

import com.yeliheng.blogsystem.domain.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "文章")
public class ArticleVo {

    private Long categoryId;

    private Long draftId;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String summary;

    @NotBlank(message = "内容不能为空")
    private String content;

    private String top;

    private String visible;

    private List<Tag> tags;

    private String createdAt;

}
