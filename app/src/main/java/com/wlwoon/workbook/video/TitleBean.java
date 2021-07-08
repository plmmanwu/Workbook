package com.wlwoon.workbook.video;

/**
 * Created by wxy on 2021/5/7.
 */

public class TitleBean {

    String name;
    boolean selected;

    public TitleBean(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
