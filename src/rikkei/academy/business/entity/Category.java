package rikkei.academy.business.entity;

import rikkei.academy.business.util.InputMethods;

import java.io.Serializable;

public class Category implements Serializable {
    private String categoryId;
    private String name;
    private String description;

    public Category() {
    }

    public Category(String categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void inputData() {
        System.out.print("Nhập tên danh mục: ");
        this.name = InputMethods.getString();
        System.out.print("Nhập mô tả danh mục: ");
        this.description = InputMethods.getString();
    }

    public void displayData() {
        System.out.println("Id danh mục: " + this.categoryId);
        System.out.println("Tên danh mục: " + this.name);
        System.out.println("Mô tả danh mục: " + this.description);
    }
}

