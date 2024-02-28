package rikkei.academy.presentation.user;

import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.entity.Post;
import rikkei.academy.business.design.IPostDesign;
import rikkei.academy.business.designImpl.PostDesignImpl;
import rikkei.academy.business.entity.User;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopMessage;

import java.util.ArrayList;
import java.util.List;

public class UserPostController {
    private static final IPostDesign postDesign = new PostDesignImpl();
    private static final IUserDesign userDesign = new UserDesignImpl();
    public static void menuPostManagementUser(String username) {
        User user = userDesign.findByUsername(username);
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                               ╔═══════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                               ║ " + "\u001B[33m" + user.getFullName() + "\u001B[32m -  QUẢN LÝ BÀI VIẾT " + "\u001B[34m");
            System.out.println("║═════════════════════╝═══════════════════════════════╚═══════════════════════════════════════║");
            System.out.println("║ \u001B[36m                        1. XEM DANH SÁCH CÁC BÀI VIẾT CỦA BẠN                               \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        2. ĐĂNG BÀI VIẾT (CẦN ĐƯỢC XÉT DUYỆT)                               \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        3. CHỈNH SỬA THÔNG TIN BÀI VIẾT CỦA BẠN                             \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        4. GỠ (XOÁ) BÀI VIẾT CỦA BẠN                                        \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        5. CHỈNH SỬA QUYỀN ĐƯỢC XEM BÀI VIẾT CỦA BẠN                        \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        0. QUAY LẠI MENU TRƯỚC                                              \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    displayAllPost(username);
                    break;
                case 2:
                    addNewPostUser(username);
                    break;
                case 3:
                    updatePost(username);
                    break;
                case 4:
                    deletePost(username);
                    break;
                case 5:
                    updateViewPermissionOfYourPosts(username);
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);;
            }
        }
    }

    private static void displayAllPost(String username) {
        List<Post> postList = postDesign.getAll();
        boolean isPost = false;
        for (Post post : postList) {
            isPost = post.getAuthor().getUsername().equals(username);
        }
        if (!isPost) {
            System.out.println("Hiện tại bạn chưa Đăng bài nào.");
        } else {
            System.out.println("===== Danh sách các bài Post của bạn:");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (Post post : postList) {
                if (post.getAuthor().getUsername().equals(username)) {
                    post.displayData(true);
                    System.out.println("-----------------------------------------------");
                }
            }
        }
    }


    private static void addNewPostUser(String username) {
        System.out.println("===== Thêm mới bài Post =====");
        Post postUser = new Post();
        postUser.setPostId(postDesign.getNewId());
        postUser.inputData(username, true);
        postDesign.save(postUser);
    }

    private static void updatePost(String username) {
        List<Post> postList = postDesign.getAll();
        boolean isPostOfUser = false;
        for (Post post : postList) {
            if (post.getAuthor().getUsername().equals(username)) {
                isPostOfUser = true;
                break;
            }
        }
        if (!isPostOfUser) {
            System.out.println("Hiện tại bạn vẫn chưa đăng Bài viết nào.");
            return;
        }
        Post post;
        while (true) {
            System.out.print("Nhập Id của bài viết bạn muốn chỉnh sửa: ");
            String postId = InputMethods.getString();
            post = postDesign.findById(postId);
            if (post == null) {
                System.err.println("Id nhập vào không đúng, hãy kiểm tra lại.");
            } else {
                break;
            }
        }
        post.inputData(username, false);
        postDesign.save(post);
    }

    private static void deletePost(String username) {
        List<Post> postList = postDesign.getAll();
        boolean isPostOfUser = false;
        for (Post post : postList) {
            isPostOfUser = post.getAuthor().getUsername().equals(username);
            break;
        }
        if (!isPostOfUser) {
            System.out.println("Hiện tại bạn đang không có Bài đăng nào.");
            return;
        }
        Post post;
        while (true) {
            System.out.print("Nhập Id của bài Post để xoá: ");
            String postIdInput = InputMethods.getString();
            post = postDesign.findById(postIdInput);
            if (post == null) {
                System.err.println("Id nhập vào không chính xác, hãy kiểm tra lại.");
            } else {
                break;
            }
        }
        postDesign.delete(post);
    }

    private static void updateViewPermissionOfYourPosts(String username) {
        List<Post> postList = postDesign.getAll();
        List<Post> postsOfUser = new ArrayList<>();
        for (Post post : postList) {
            if (post.getAuthor().getUsername().equals(username)) {
                postsOfUser.add(post);
            }
        }
        if (postsOfUser.isEmpty()) {
            System.err.println(ShopMessage.NO_POST_ERROR);
            return;
        }
        Post post = null;
        while (true) {
            System.out.print("Nhập Id của bài Post để thay đổi thông tin: ");
            String idPostInput = InputMethods.getString();
            for (Post post1 : postsOfUser) {
                if (post1.getPostId().equals(idPostInput)) {
                    post = post1;
                    break;
                }
            }
            if (post == null) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                break;
            }
        }
        post.selectPermissionViewPost();
        postDesign.save(post);
    }
}
