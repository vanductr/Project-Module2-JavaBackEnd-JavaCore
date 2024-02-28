package rikkei.academy.presentation.run;


import rikkei.academy.business.entity.ViewPermission;
import rikkei.academy.business.util.ShopMessage;
import rikkei.academy.presentation.admin.LoginController;
import rikkei.academy.business.entity.Post;
import rikkei.academy.business.design.IPostDesign;
import rikkei.academy.business.designImpl.PostDesignImpl;
import rikkei.academy.business.util.InputMethods;

import java.util.List;

public class MainMenu {
    private static final IPostDesign postDesign = new PostDesignImpl();
    public static void main(String[] args) {
        do {
            System.out.println("\u001B[34m╔══════════════════════════════ MENU CHÍNH ════════════════════════════════╗");
            System.out.println("║ \u001B[36m                       1. ĐĂNG KÝ - ĐĂNG NHẬP                            \u001B[34m║");
            System.out.println("║══════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       2. XEM BÀI VIẾT CỦA TẤT CẢ MỌI NGƯỜI              \u001B[34m║");
            System.out.println("║══════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       0. THOÁT CHƯƠNG TRÌNH                             \u001B[34m║");
            System.out.println("\u001B[34m╚══════════════════════════════════════════════════════════════════════════╝");
            System.out.print("   Nhập lựa chọn của bạn: ");


            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    LoginController.displayMenuLogin();
                    break;
                case 2:
                    displayAllPost();
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình.");
                    System.exit(0);
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        } while (true);
    }

    public static void displayAllPost() {
        List<Post> postList = postDesign.getAll();
        boolean hasPublicPost = false;
        boolean hasApprovedPost = false;
        for (Post post : postList) {
            if (post.getPermissionViewPost() == ViewPermission.PUBLIC) {
                hasPublicPost = true;
                if (post.isApproved()) {
                    hasApprovedPost = true;
                }
            }
        }

        if (!hasPublicPost || !hasApprovedPost) {
            System.out.println("============= Danh sách các Post đang trống.");
            return;
        }
        System.out.println("======================== DANH SÁCH TẤT CẢ CÁC BÀI VIẾT: ========================");
        for (Post post : postList) {
            if (post.isApproved() && post.getPermissionViewPost() == ViewPermission.PUBLIC) {
                post.displayDataPost();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        System.out.println("\u001B[33m" + "Bạn cần Đăng nhập để có thể Like - Comment - Share - Report Bài viết.");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════");
    }
}

