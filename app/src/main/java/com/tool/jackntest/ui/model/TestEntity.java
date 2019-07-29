package com.tool.jackntest.ui.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class TestEntity implements MultiItemEntity {

    public int itemType;

    public static class TopEntity {
        public String name;
        public boolean isCheck = false;
        public TopEntity(String name, boolean isCheck) {
            this.name = name;
            this.isCheck = isCheck;
        }
    }

    public static class ContetEntity {
        public String name;
        public boolean isCheck = false;
        public ContetEntity(String name, boolean isCheck) {
            this.name = name;
            this.isCheck = isCheck;
        }
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
