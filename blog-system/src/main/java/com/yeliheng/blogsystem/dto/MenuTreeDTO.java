package com.yeliheng.blogsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yeliheng.blogsystem.entity.Menu;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端菜单选择树传输对象
 */
public class MenuTreeDTO {
    private Long id;

    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuTreeDTO> children;

    public MenuTreeDTO(Menu menu) {
        this.id = menu.getId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(MenuTreeDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<MenuTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeDTO> children) {
        this.children = children;
    }
}
