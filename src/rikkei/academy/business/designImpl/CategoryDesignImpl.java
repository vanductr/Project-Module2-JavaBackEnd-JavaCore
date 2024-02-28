package rikkei.academy.business.designImpl;

import rikkei.academy.business.entity.Category;
import rikkei.academy.business.design.ICategoryDesign;
import rikkei.academy.business.util.IOFile;
import rikkei.academy.business.util.ShopConstants;

import java.util.List;

public class CategoryDesignImpl implements ICategoryDesign {
    private static List<Category> categoryList;

    public CategoryDesignImpl() {
        categoryList = IOFile.readFromFile(ShopConstants.CATEGORY_PATH);
    }

    @Override
    public List<Category> getAll() {
        return categoryList;
    }

    @Override
    public void save(Category category) {
        if (findById(category.getCategoryId()) == null) {
            categoryList.add(category);
            System.out.println("Đã thêm thành công 1 Danh mục mới.");
        } else {
            categoryList.set(categoryList.indexOf(findById(category.getCategoryId())), category);
            System.out.println("Đã sửa đổi thành công Danh mục.");
        }
        IOFile.writeToFile(ShopConstants.CATEGORY_PATH, categoryList);
    }

    @Override
    public Category findById(String categoryId) {
        for (Category category : categoryList) {
            if (category.getCategoryId().equals(categoryId)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public void delete(Category category) {
        categoryList.remove(category);
        System.out.println("Đã xoá thành công Danh mục.");
        IOFile.writeToFile(ShopConstants.CATEGORY_PATH, categoryList);
    }

    @Override
    public String getNewId() {
        int idMax = 0;
        for (Category category : categoryList) {
            int categoryId = Integer.parseInt(category.getCategoryId().replace("C", "0"));
            if (idMax < categoryId) {
                idMax = categoryId;
            }
        }
        idMax += 1;
        return "C" + String.format("%03d", idMax);
    }
}
