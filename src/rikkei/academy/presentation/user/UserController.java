package rikkei.academy.presentation.user;

import rikkei.academy.business.design.ICommentDesign;
import rikkei.academy.business.design.IPostDesign;
import rikkei.academy.business.design.IReportDesign;
import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.designImpl.CommentDesignImpl;
import rikkei.academy.business.designImpl.PostDesignImpl;
import rikkei.academy.business.designImpl.ReportDesignImpl;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.entity.*;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopMessage;

import java.time.LocalDate;
import java.util.List;

public class UserController {
    private static final IUserDesign userDesign = new UserDesignImpl();
    private static final IPostDesign postDesign = new PostDesignImpl();
    private static final ICommentDesign commentDesign = new CommentDesignImpl();
    private static final IReportDesign reportDesign = new ReportDesignImpl();
    public static void displayUserMenu(String username) {
        User user = userDesign.findByUsername(username);
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                               ╔═══════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                               ║   " + "\u001B[32m XIN CHÀO " + "\u001B[33m" + user.getFullName() + "\u001B[34m");
            System.out.println("║═════════════════════╝═══════════════════════════════╚═══════════════════════════════════════║");
            System.out.println("║ \u001B[36m                        1. XEM TẤT CẢ DANH SÁCH BÀI VIẾT                                    \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        2. QUẢN LÝ BÀI VIẾT CỦA BẠN                                         \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        3. QUẢN LÝ BẠN BÈ                                                   \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        4. QUẢN LÝ GROUP/ TÌM KIẾM GROUP                                    \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        5. XEM THÔNG TIN CÁ NHÂN                                            \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        6. CHỈNH SỬA THÔNG TIN CÁ NHÂN                                      \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        7. CHỈNH SỬA THÔNG TIN ĐĂNG NHẬP (USERNAME - PASSWORD)              \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        0. ĐĂNG XUẤT                                                        \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════╝");

            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    displayAllPostIsLogin(username);
                    break;
                case 2:
                    UserPostController.menuPostManagementUser(username);
                    break;
                case 3:
                    FriendController.displayFriendManagementMenu(username);
                    break;
                case 4:
                    UserGroupController.displayUserGroupMenu(username);
                    break;
                case 5:
                    displayPersonalInformation(username);
                    break;
                case 6:
                    editPersonalInformation(username);
                    break;
                case 7:
                    editLoginInformation(username);
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ. Hãy kiểm tra lại.");
            }
        }
    }

    private static void displayAllPostIsLogin(String username) {
        List<Post> postList = postDesign.getAll();
        User userIsLogin = userDesign.findByUsername(username);
        boolean canViewAnyPost = false;
        for (Post post : postList) {
            if (isPostVisible(post, userIsLogin)) {
                canViewAnyPost = true;
                break;
            }
        }
        if (!canViewAnyPost) {
            System.out.println("Bạn không có quyền xem bất kỳ bài viết nào.");
            return;
        }
        System.out.println("======================== DANH SÁCH TẤT CẢ CÁC BÀI VIẾT: ========================");
        for (Post post : postList) {
            if (isPostVisible(post, userIsLogin)) {
                post.displayDataPost();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        System.out.println("\u001B[32m" + "============== BẠN CÓ MUỐN LIKE - COMMENT - SHARE - REPORT BÀI VIẾT NÀO KHÔNG?");
        System.out.println("1. Có");
        System.out.println("0. Không");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                likeCommentSharePost(username);
                break;
            } else if (choice == 0) {
                break;
            } else {
                System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static boolean isPostVisible(Post post, User user) {
        if (post.getPermissionViewPost() == ViewPermission.PUBLIC) {
            return true;
        } else if (post.getPermissionViewPost() == ViewPermission.FRIENDS) {
            for (User friend : user.getFriends()) {
                if (friend.getUserId().equals(post.getAuthor().getUserId())) {
                    return true;
                }
            }
            if (post.getAuthor().getUserId().equals(user.getUserId())) {
                return true;
            }
        } else if (post.getAuthor().getUserId().equals(user.getUserId())) {
            return true;
        }
        return false;
    }


    private static void likeCommentSharePost(String username) {
        User userIsLogin = userDesign.findByUsername(username);
        System.out.println("────────────────────────────────────────────────────────────────────────────────────────");
        Post post;
        while (true) {
            System.out.print("Nhập Post Id để Like - Comment - Share Bài Đăng đó: ");
            String postId = InputMethods.getString();
            post = postDesign.findById(postId);
            if (post == null) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                break;
            }
        }
        System.out.println("===== Đây là bài đăng bạn muốn tương tác:");
        post.displayDataPost();
        System.out.println("1. Like/ Bỏ Like Bài viết");
        System.out.println("2. Comment Bài viết");
        System.out.println("3. Share Bài viết");
        System.out.println("4. Report Bài viết");
        System.out.println("0. Quay lại");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            int choice2 = InputMethods.getInteger();
            switch (choice2) {
                case 1:
                    Like like = new Like(userIsLogin);
                    List<Like> likeList = post.getLikes();
                    if (likeList.isEmpty()) {
                        likeList.add(like);
                        System.out.println("Bạn vừa like 1 bài viết");
                    } else {
                        for (Like like1 : likeList) {
                            if (like1.getUser().getUserId().equals(like.getUser().getUserId())) {
                                likeList.remove(like);
                                System.out.println("Bạn vừa bỏ like 1 bài viết");
                            } else {
                                likeList.add(like);
                                System.out.println("Bạn vừa like 1 bài viết");
                            }
                        }
                    }
                    post.setLikes(likeList);
                    postDesign.save(post);
                    return;
                case 2:
                    Comment comment = new Comment();
                    comment.setCommentId(commentDesign.getNewId());
                    comment.inputData();
                    List<Comment> commentList = post.getCommentList();
                    commentList.add(comment);
                    post.setCommentList(commentList);
                    postDesign.save(post);
                    System.out.println("Bạn vừa comment 1 bài viết");
                    return;
                case 3:
                    Share share = new Share(userIsLogin);
                    List<Share> shareList = post.getShares();
                    shareList.add(share);
                    post.setShares(shareList);
                    postDesign.save(post);
                    System.out.println("Bạn vừa chia sẻ 1 bài viết.");
                    return;
                case 4:
                    Report report = new Report();
                    List<Report> reportList = post.getReportList();
                    report.setReportId(reportDesign.getNewId());
                    report.inputDataReport();
                    report.setReporterUser(userIsLogin);
                    reportDesign.save(report);
                    reportList.add(report);
                    post.setReportList(reportList);
                    postDesign.save(post);
                    System.out.println("Bạn vừa Báo cáo bài viết này.");
                    return;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void displayPersonalInformation(String username) {
        List<User> userList = userDesign.getAll();
        System.out.println("===== Thông tin cá nhân của bạn:");
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                user.displayData();
                break;
            }
        }
    }

    private static void editPersonalInformation(String username) {
        List<User> userList = userDesign.getAll();
        User userEdit = null;
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                userEdit = user;
                break;
            }
        }
        if (userEdit == null) return; // Dòng này không cần(để tránh lỗi vàng của IntelliJ)
        System.out.println("===== Chỉnh sửa thông tin cá nhân:");
        System.out.print("Email: ");
        userEdit.setEmail(InputMethods.getString());
        System.out.print("Họ và tên: ");
        userEdit.setFullName(InputMethods.getString());
        System.out.print("Ngày sinh (yyyy-MM-dd): ");
        String dobString = InputMethods.getString();
        userEdit.setDateOfBirth(LocalDate.parse(dobString));
        userDesign.save(userEdit);
    }

    private static void editLoginInformation(String username) {
        List<User> userList = userDesign.getAll();
        User userEdit = null;
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                userEdit = user;
                break;
            }
        }
        if (userEdit == null) return; // Dòng này không cần(để tránh lỗi vàng của IntelliJ)
        System.out.println("===== Chỉnh sửa thông tin Đăng nhập:");
        System.out.print("Tên đăng nhập: ");
        userEdit.setUsername(InputMethods.getString());
        System.out.print("Mật khẩu: ");
        userEdit.setPassword(InputMethods.getString());
        userDesign.save(userEdit);
        System.out.println("Đã thay đổi thành công thông tin Đăng nhập");
    }
}
