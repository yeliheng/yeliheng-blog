package com.yeliheng.blogsystem.domain;

import java.util.List;

/**
 * 前端路由信息
 */
public class Router {

    private Long id;

    private String name;

    private String path;

    private String component;

    private String icon;

    private String permission;

    private Integer hidden;

    private List<Router> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public List<Router> getChildren() {
        return children;
    }

    public void setChildren(List<Router> children) {
        this.children = children;
    }
}
