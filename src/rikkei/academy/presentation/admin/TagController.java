package rikkei.academy.presentation.admin;

import rikkei.academy.business.entity.Tag;
import rikkei.academy.business.design.ITagDesign;
import rikkei.academy.business.designImpl.TagDesignImpl;
import rikkei.academy.business.util.InputMethods;

import java.util.List;

public class TagController {
    private static final ITagDesign tagDesign = new TagDesignImpl();
    public static void showTagManagementMenu() {
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                     ╔═════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                     ║" + "\u001B[32m     ADMIN   " + "\u001B[33m" + "   QUẢN LÝ HASHTAG" + "\u001B[34m      ║");
            System.out.println("║═════════════════════╝═════════════════════╚═════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       1. HIỂN THỊ TẤT CẢ CÁC TAG                               \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       2. THÊM MỚI MỘT TAG                                      \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       3. CHỈNH SỬA THÔNG TIN MỘT TAG                           \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       4. XOÁ THÔNG TIN MỘT TAG                                 \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       0. QUAY LẠI MENU TRƯỚC                                   \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    displayAllTag();
                    break;
                case 2:
                    addNewTag();
                    break;
                case 3:
                    updateTagInfo();
                    break;
                case 4:
                    deleteTag();
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lý, hãy kiểm tra lại.");
            }
        }
    }

    private static void displayAllTag() {
        List<Tag> tagList = tagDesign.getAll();
        if (tagList.isEmpty()) {
            System.out.println("Danh sách các Tag trống.");
            return;
        }
        System.out.println("====== Danh Sách Các Tag Hiện Có: ======");
        for (Tag tag : tagList) {
            tag.displayData();
            System.out.println("-------------------");
        }
    }

    private static void addNewTag() {
        System.out.println("=== Thêm Tag mới ===");
        Tag tag = new Tag();
        tag.setTagId(tagDesign.getNewId());
        tag.inputData();
        tagDesign.save(tag);
    }

    private static void updateTagInfo() {
        Tag tag;
        while (true) {
            System.out.print("Nhập Id của Tag để thay đổi thông tin: ");
            String tagId = InputMethods.getString();
            tag = tagDesign.findById(tagId);
            if (tag == null) {
                System.err.println("Id nhập vào không đúng, hãy kiểm tra lại.");
            } else {
                break;
            }
        }
        tag.inputData();
        tagDesign.save(tag);
    }

    private static void deleteTag() {
        Tag tag;
        while (true) {
            System.out.print("Nhập Id của Tag cần xoá: ");
            String tagId = InputMethods.getString();
            tag = tagDesign.findById(tagId);
            if (tag == null) {
                System.err.println("Id nhập vào không đúng, hãy kiểm tra lại.");
            } else {
                break;
            }
        }
        tagDesign.delete(tag);
    }
}
