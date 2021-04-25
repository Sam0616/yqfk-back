package com.ly.bigdata.vo;

import java.util.List;

public class MenuTree {
    private Integer id;
    private String label;
    private List<MenuTree> children;

    public MenuTree(int id, String name, List<MenuTree> list) {
        setId(id);
        setLabel(name);
        setChildren(list);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<MenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }
}
