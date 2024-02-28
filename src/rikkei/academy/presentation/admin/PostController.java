package rikkei.academy.presentation.admin;

import rikkei.academy.business.design.IPostDesign;
import rikkei.academy.business.design.IReportDesign;
import rikkei.academy.business.designImpl.ReportDesignImpl;
import rikkei.academy.business.entity.Category;
import rikkei.academy.business.entity.Post;
import rikkei.academy.business.designImpl.PostDesignImpl;
import rikkei.academy.business.entity.Report;
import rikkei.academy.business.util.IOFile;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopConstants;
import rikkei.academy.business.util.ShopMessage;

import java.util.ArrayList;
import java.util.List;

public class PostController {
    private static final IPostDesign postDesign = new PostDesignImpl();
    private static final IReportDesign reportDesign = new ReportDesignImpl();
    public static void showPostManagementMenu() {
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                     ╔═════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                     ║   " + "\u001B[32m ADMIN  " + "\u001B[33m" + "  QUẢN LÝ BÀI VIẾT" + "\u001B[34m        ║");
            System.out.println("║═════════════════════╝═════════════════════╚═════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       1. HIỂN THỊ TẤT CẢ BÀI VIẾT HIỆN TẠI                     \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       2. XOÁ BÀI VIẾT                                          \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       3. DUYỆT BÀI VIẾT                                        \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       4. XEM CÁC BÁO CÁO VI PHẠM VỀ BÀI VIẾT                   \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       5. XEM BÀI VIẾT THEO DANH MỤC                            \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       6. XEM BÀI VIẾT THEO TÁC GIẢ                             \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       0. QUAY LẠI MENU TRƯỚC                                   \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    displayAllPost();
                    break;
                case 2:
                    deletePost();
                    break;
                case 3:
                    reviewPost();
                    break;
                case 4:
                    viewPostViolationReports();
                    break;
                case 5:
                    viewPostsByCategory();
                    break;
                case 6:
                    viewPostsByAuthor();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại.");
            }
        }
    }

    private static void displayAllPost() {
        List<Post> postList = postDesign.getAll();
        if (postList.isEmpty()) {
            System.out.println("Danh sách bài viết đang trống.");
            return;
        }
        System.out.println("===== Danh sách các bài Post ======");
        for (Post post : postList) {
            post.displayData(true);
            System.out.println("------------------------------");
        }
    }

    private static void reviewPost() {
        List<Post> postList = postDesign.getAll();
        boolean isApproved = false;
        for (Post post : postList) {
            if (!post.isApproved()) {
                isApproved = true;
                break;
            }
        }
        if (!isApproved) {
            System.out.println("Danh sách bài viết chưa duyệt đang trống.");
            return;
        }
        System.out.println("====== Danh sách bài Post chưa Duyệt:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (Post post : postList) {
            if (!post.isApproved()) {
                post.displayData(true);
                System.out.println("----------------------------------------------");
            }
        }
        Post post;
        while (true) {
            System.out.print("Nhập Id của Bài Đăng để Xét duyệt hoặc Xoá: ");
            String postId = InputMethods.getString();
            post = postDesign.findById(postId);
            if (post == null) {
                System.err.println("Id nhập vào không hợp lệ, hãy kiểm tra lại.");
            } else {
                break;
            }
        }
        while (true) {
            System.out.println("========== XÉT DUYỆT BÀI POST ===============");
            System.out.println("1. Bài đăng hợp lệ. XÉT DUYỆT Bài viết");
            System.out.println("2. Bài đăng bị vi phạm. Không Xét duyệt. Xoá Bài đăng");
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    post.setApproved(true);
                    System.out.println("Đã xét duyệt một bài POST.");
                    IOFile.writeToFile(ShopConstants.POST_PATH, postList);
                    return;
                case 2:
                    postDesign.delete(post);
                    System.out.println("Đã xoá 1 bài POST vi phạm.");
                    return;
                default:
                    System.err.println("Lựa chọn không hơp lệ, hãy kiểm tra lại.");
            }
        }
    }

    private static void deletePost() {
        Post post;
        while (true) {
            System.out.print("Nhập Id của Post cần xoá: ");
            String postIdInput = InputMethods.getString();
            post = postDesign.findById(postIdInput);
            if (post == null) {
                System.err.println("Id nhập vào không đúng, hãy kiểm tra lại.");
            } else {
                break;
            }
        }
        postDesign.delete(post);
        System.out.println("Đã xoá thành công 1 bài POST");
    }

    private static void viewPostViolationReports() {
        List<Post> postList = postDesign.getAll();
        List<Post> postsReported = new ArrayList<>();
        for (Post post : postList) {
            List<Report> reportList = post.getReportList();
            for (Report report : reportList) {
                if (!report.getStatus()) {
                    postsReported.add(post);
                    break;
                }
            }
        }
        if (postsReported.isEmpty()) {
            System.out.println("===== Hiện tại không có Bài viết nào bị Báo cáo");
            return;
        }
        for (Post post : postsReported) {
            List<Report> reportList = post.getReportList();
            for (Report report : reportList) {
                if (!report.getStatus()) {
                    System.out.println("===== Bài viết bị Báo cáo:");
                    post.displayDataPost();
                    System.out.println("------------------------------------------------------");
                    System.out.println("===== Nội dung Báo cáo:");
                    report.displayData();
                    System.out.println("---------------------------------------------------------");
                }
            }
        }

        System.out.println("===== Bạn có muốn Xử lý Bài viết bị Báo cáo nào không ?");
        System.out.println("1. Báo cáo không Đúng (Vu khống)");
        System.out.println("2. Báo cáo đúng, Xoá bài viết này.");
        System.out.println("0. Thoát");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    Post report = null;
                    while (true) {
                        System.out.print("Nhập Id của Bài viết(Post) để xử lý: ");
                        String reportId = InputMethods.getString();
                        for (Post post : postsReported) {
                            if (post.getPostId().equals(reportId)) {
                                report = post;
                                break;
                            }
                        }
                        if (report == null) {
                            System.err.println(ShopMessage.NO_ID_ERROR);
                        } else {
                            break;
                        }
                    }
                    List<Report> reportList = report.getReportList();
                    for (Report report1 : reportList) {
                        report1.setStatus(true);
                        reportDesign.save(report1);
                    }
                    postDesign.save(report);
                    System.out.println("===== Đã xử lý các Bài Báo cáo.");
                    return;
                case 2:
                    Post report2 = null;
                    while (true) {
                        System.out.print("Nhập Id của Bài viết(Post) để xử lý: ");
                        String reportId = InputMethods.getString();
                        for (Post post : postsReported) {
                            if (post.getPostId().equals(reportId)) {
                                report2 = post;
                                break;
                            }
                        }
                        if (report2 == null) {
                            System.err.println(ShopMessage.NO_ID_ERROR);
                        } else {
                            break;
                        }
                    }
                    List<Report> reportList2 = report2.getReportList();
                    for (Report report1 : reportList2) {
                        report1.setStatus(true);
                        reportDesign.save(report1);
                    }
                    postDesign.delete(report2);
                    System.out.println("===== Đã xoá bài Đăng vi phạm và xử lý các báo cáo.");
                    return;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void viewPostsByCategory() {
        List<Post> posts = postDesign.getAll();
        System.out.print("Nhập Tên Danh mục bài viết để tìm kiếm: ");
        String categoryName = InputMethods.getString();
        List<Post> postList = new ArrayList<>();
        for (Post post : posts) {
            List<Category> categoryList = post.getCategories();
            for (Category category : categoryList) {
                if (category.getName().equals(categoryName)) {
                    postList.add(post);
                }
            }
        }
        if (postList.isEmpty()) {
            System.err.println(ShopMessage.NO_NAME_ERROR);
            return;
        }
        System.out.println("===== Danh sách Các bài Post theo Danh mục " + categoryName + " là:");
        for (Post post : postList) {
            post.displayDataPost();
            System.out.println("----------------------------------------------------------------");
        }
    }

    private static void viewPostsByAuthor() {
        List<Post> postList = postDesign.getAll();
        System.out.print("Nhập Tên Tác giả để Hiển thị bài viết của họ: ");
        String authorName = InputMethods.getString();
        List<Post> posts = new ArrayList<>();
        for (Post post : postList) {
            if (post.getAuthor().getUsername().equals(authorName)) {
                posts.add(post);
            }
        }
        if (posts.isEmpty()) {
            System.err.println(ShopMessage.NO_NAME_ERROR);
            return;
        }
        System.out.println("===== Danh sách các Bài viết của Tác giả " + authorName + " là:");
        for (Post post : posts) {
            post.displayDataPost();
            System.out.println("-------------------------------------------");
        }
    }
}
