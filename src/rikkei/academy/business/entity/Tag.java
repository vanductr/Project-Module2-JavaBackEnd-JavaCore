package rikkei.academy.business.entity;

import rikkei.academy.business.util.InputMethods;

import java.io.Serializable;

public class Tag implements Serializable {
    private String tagId;
    private String name;

    public Tag() {
    }

    public Tag(String tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void inputData() {
        System.out.print("Nhập tên Tag: ");
        this.name = InputMethods.getString();
    }

    public void displayData() {
        System.out.println("Tag ID: " + this.tagId);
        System.out.println("Tag Name: " + this.name);
    }
}
