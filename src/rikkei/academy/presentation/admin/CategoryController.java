package rikkei.academy.presentation.admin;

import rikkei.academy.business.design.ICategoryDesign;
import rikkei.academy.business.entity.Category;
import rikkei.academy.business.designImpl.CategoryDesignImpl;
import rikkei.academy.business.util.InputMethods;

import java.util.List;

public class CategoryController {
    static ICategoryDesign categoryDesign = new CategoryDesignImpl();
    public static void displayCategoryManagementMenu() {
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                     ╔═════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                     ║" + "\u001B[32m ADMIN   " + "\u001B[33m" + "QUẢN LÝ DANH MỤC BÀI VIẾT" + "\u001B[34m   ║");
            System.out.println("║═════════════════════╝═════════════════════╚═════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       1. XEM DANH SÁCH DANH MỤC                                \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       2. THÊM MỚI DANH MỤC                                     \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       3. SỬA THÔNG TIN DANH MỤC                                \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       4. XOÁ DANH MỤC                                          \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       0. QUAY LẠI MENU TRƯỚC                                   \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    displayCategory();
                    break;
                case 2:
                    addNewCategory();
                    break;
                case 3:
                    updateCategory();
                    break;
                case 4:
                    deleteCategory();
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ. Hãy kiểm tra lại.");
            }
        }
    }

    private static void displayCategory() {
        List<Category> categoryList = categoryDesign.getAll();
        System.out.println("============ Danh sách Danh mục: ==========");
        for (Category category : categoryList) {
            category.displayData();
            System.out.println("-------------------------------");
        }
    }

    private static void addNewCategory() {
        Category category = new Category();
        category.setCategoryId(categoryDesign.getNewId());
        System.out.println("========= Thêm mới Danh mục ========");
        category.inputData();
        categoryDesign.save(category);
    }

    private static void updateCategory() {
        Category category;
        while (true) {
            System.out.print("Nhập Id của Danh mục cần sửa: ");
            String categoryId = InputMethods.getString();
            category = categoryDesign.findById(categoryId);
            if (category == null) {
                System.err.println("Id nhập vào không đúng, hãy kiểm tra lại.");
            } else {
                break;
            }
        }
        category.inputData();
        categoryDesign.save(category);
    }

    private static void deleteCategory() {
        Category category;
        while (true) {
            System.out.print("Nhập Id của Danh mục cần xoá: ");
            String categoryId = InputMethods.getString();
            category = categoryDesign.findById(categoryId);
            if (category == null) {
                System.err.println("Id nhập vào không đúng, hãy kiểm tra lại.");
            } else {
                break;
            }
        }
        categoryDesign.delete(category);
    }
}
