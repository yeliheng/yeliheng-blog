package com.yeliheng.blogsystem.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Article {
    @Id
    private Long id;

    private Long user_id;

    private String title;

    private String summary;

    private String content;

    private String url;

    private Integer vivible;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
