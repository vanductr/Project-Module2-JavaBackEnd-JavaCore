package rikkei.academy.presentation.admin;

import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.entity.User;
import rikkei.academy.business.entity.UserRole;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopMessage;
import rikkei.academy.presentation.user.UserController;

public class LoginController {
    private static final IUserDesign userDesign = new UserDesignImpl();
    public static void displayMenuLogin() {
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════════════ ĐĂNG KÝ - ĐĂNG NHẬP ════════════════════════╗");
            System.out.println("║ \u001B[36m                             1. ĐĂNG KÝ                                  \u001B[34m║");
            System.out.println("║══════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                             2. ĐĂNG NHẬP                                \u001B[34m║");
            System.out.println("║══════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                             0. QUAY LẠI MENU TRƯỚC                      \u001B[34m║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
            System.out.print(" Nhập lựa chọn của bạn: ");
            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void registerUser() {
        System.out.println("************************** ĐĂNG KÝ TÀI KHOẢN **************************");
        User newUser = new User();
        newUser.setUserId(userDesign.getNewId());
        newUser.inputData();
        userDesign.register(newUser);
    }

    private static void loginUser() {
        System.out.println("************************** ĐĂNG NHẬP **************************");
        System.out.print("Tên người dùng: ");
        String username = InputMethods.getString();
        System.out.print("Mật khẩu: ");
        String password = InputMethods.getString();

        UserRole role = userDesign.login(username, password);

        if (role == UserRole.ADMIN) {
            AdminController.displayAdminMenu();
        } else if (role == UserRole.USER) {
            User user = userDesign.findByUsername(username);
            if (user.isBlocked()) {
                UserController.displayUserMenu(username);
            } else {
                System.err.println(ShopMessage.BLOCKED_ERROR);
            }
        } else {
            System.err.println(ShopMessage.LOGIN_ERROR);
        }
    }
}
