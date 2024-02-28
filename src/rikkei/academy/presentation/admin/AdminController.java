package rikkei.academy.presentation.admin;

import rikkei.academy.business.design.IReportDesign;
import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.designImpl.ReportDesignImpl;
import rikkei.academy.business.entity.Report;
import rikkei.academy.business.entity.User;
import rikkei.academy.business.entity.UserRole;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.util.IOFile;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopConstants;
import rikkei.academy.business.util.ShopMessage;

import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private static final IUserDesign userDesign = new UserDesignImpl();
    private static final IReportDesign reportDesign = new ReportDesignImpl();
    public static void displayAdminMenu() {
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                     ╔═════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                     ║   " + "\u001B[32m MENU QUẢN LÝ CỦA  " + "\u001B[33m" + "  ADMIN" + "\u001B[34m        ║");
            System.out.println("║═════════════════════╝═════════════════════╚═════════════════════════════════════║");
            System.out.println("║ \u001B[36m                            1. QUẢN LÝ NGƯỜI DÙNG                               \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                            2. QUẢN LÝ GROUP                                    \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                            3. QUẢN LÝ BÀI VIẾT                                 \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                            4. QUẢN LÝ DANH MỤC BÀI VIẾT                        \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                            5. QUẢN LÝ CÁC TAG                                  \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                            0. ĐĂNG XUẤT                                        \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    displayUserManagementMenu();
                    break;
                case 2:
                    AdminGroupController.showAdminGroupManagementMenu();
                    break;
                case 3:
                    PostController.showPostManagementMenu();
                    break;
                case 4:
                    CategoryController.displayCategoryManagementMenu();
                    break;
                case 5:
                    TagController.showTagManagementMenu();
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    public static void displayUserManagementMenu() {
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                     ╔═════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                     ║   " + "\u001B[32m ADMIN  " + "\u001B[33m" + "  QUẢN LÝ NGƯỜI DÙNG" + "\u001B[34m      ║");
            System.out.println("║═════════════════════╝═════════════════════╚═════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       1. XEM DANH SÁCH TẤT CẢ NGƯỜI DÙNG                       \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       2. TÌM KIẾM NGƯỜI DÙNG THEO TÊN                          \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       3. XOÁ NGƯỜI DÙNG                                        \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       4. XEM NHỮNG BÁO CÁO VI PHẠM CỦA NGƯỜI DÙNG              \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       5. CHẶN / BỎ CHẶN QUYỀN HOẠT ĐỘNG CỦA NGƯỜI DÙNG         \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       0. QUAY LẠI MENU TRƯỚC                                   \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice = InputMethods.getInteger();

            switch (choice) {
                case 1:
                    displayAllUser();
                    break;
                case 2:
                    findUserByName();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    displayUserViolationReports();
                    break;
                case 5:
                    blockUnblockUser();
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void displayAllUser() {
        List<User> userList = userDesign.getAll();
        System.out.println("=========== DANH SÁCH NGƯỜI DÙNG ===========");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (User user : userList) {
            if (user.getRole() == UserRole.USER){
                user.displayData();
                System.out.println("--------------------------------------------");
            }
        }
    }

    private static void findUserByName() {
        System.out.print("Nhập tên người dùng(username) cần tìm kiếm: ");
        String username = InputMethods.getString();
        User user = userDesign.findByUsername(username);
        if (user == null) {
            System.out.println("Không tìm thấy người dùng có username là: " + username);
            return;
        }
        System.out.println("Thông tin người dùng có username: " + username + " là:");
        user.displayData();
    }

    private static void deleteUser() {
        System.out.print("Nhập Id của người dùng cần xoá: ");
        String userIdInput = InputMethods.getString();
        User user = userDesign.findById(userIdInput);
        if (user == null) {
            System.out.println("Không tìm thấy User nào có Id: " + userIdInput + ". Hãy kiểm tra lại.");
            return;
        }
        userDesign.delete(user);
        System.out.println("Đã xoá thành công User có Id: " + userIdInput);
    }
    
    private static void displayUserViolationReports() {
        List<User> userList = userDesign.getAll();
        List<User> usersReported = new ArrayList<>();
        for (User user : userList) {
            if (!user.getReportList().isEmpty()) {
                usersReported.add(user);
            }
        }
        if (usersReported.isEmpty()) {
            System.out.println("══════════ KHÔNG CÓ NGƯỜI DÙNG NÀO BỊ BÁO CÁO VI PHẠM.");
            return;
        }
        System.out.println("══════════ DANH SÁCH NHỮNG NGƯỜI DÙNG BỊ BÁO CÁO.");
        for (User user : usersReported) {
            System.out.println("════ NGƯỜI DÙNG: ");
            user.displayData();
            List<Report> reportList = user.getReportList();
            System.out.println("────────── NỘI DUNG BÁO CÁO:");
            for (Report report : reportList) {
                report.displayData();
                System.out.println("---------------");
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        System.out.println("══════ XỬ LÝ CÁC BÁO CÁO VI PHẠM:");
        System.out.println("1. Các Báo Cáo Đúng, Chặn Người dùng");
        System.out.println("2. Báo cáo Vu khống, Xoá các Báo cáo cho Người dùng");
        System.out.println("0. Tạm thời thoát, chưa xử lý");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    User user = null;
                    while (true) {
                        System.out.print("Nhập Id của Người Dùng để chặn họ: ");
                        String userId = InputMethods.getString();
                        for (User user1 : usersReported) {
                            if (user1.getUserId().equals(userId)) {
                                user = user1;
                                break;
                            }
                        }
                        if (user == null) {
                            System.err.println(ShopMessage.NO_ID_ERROR);
                        } else {
                            break;
                        }
                    }
                    user.setBlocked(false);
                    List<Report> reportList = user.getReportList();
                    List<Report> reports = reportDesign.getAll();
                    List<Report> reportsToRemove = new ArrayList<>();
                    for (Report report : reports) {
                        for (Report report1 : reportList) {
                            if (report.getReportId().equals(report1.getReportId())) {
                                reportsToRemove.add(report);
                            }
                        }
                    }
                    for (Report report : reportsToRemove) {
                        reportDesign.delete(report);
                    }
                    reportList.clear();
                    user.setReportList(reportList);
                    userDesign.save(user);
                    System.out.println("══════════ Bạn đã chặn người dùng " + user.getFullName());
                    System.out.println("──────────────────────────────────────────────────────────────────────────────────────────");
                    return;
                case 2:
                    User user2 = null;
                    while (true) {
                        System.out.print("Nhập Id của Người Dùng để chặn họ: ");
                        String userId = InputMethods.getString();
                        for (User user1 : usersReported) {
                            if (user1.getUserId().equals(userId)) {
                                user2 = user1;
                                break;
                            }
                        }
                        if (user2 == null) {
                            System.err.println(ShopMessage.NO_ID_ERROR);
                        } else {
                            break;
                        }
                    }
                    List<Report> reportList2 = user2.getReportList();
                    List<Report> reports2 = reportDesign.getAll();
                    List<Report> reportsToRemove2 = new ArrayList<>();
                    for (Report report : reports2) {
                        for (Report report1 : reportList2) {
                            if (report.getReportId().equals(report1.getReportId())) {
                                reportsToRemove2.add(report);
                            }
                        }
                    }
                    for (Report report : reportsToRemove2) {
                        reportDesign.delete(report);
                    }
                    reportList2.clear();
                    user2.setReportList(reportList2);
                    userDesign.save(user2);
                    System.out.println("══════════ Bạn đã Xoá các Báo cáo Vi phạm của người dùng " + user2.getFullName());
                    System.out.println("──────────────────────────────────────────────────────────────────────────────────────────");
                    return;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    public static void blockUnblockUser() {
        System.out.print("Nhập ID của User muốn đổi trạng thái: ");
        String userIdInput = InputMethods.getString();
        User user = userDesign.findById(userIdInput);
        if (user != null) {
            System.out.print("Cài đặt trạng thái cho User(true-Bỏ chặn/ false-Chặn): ");
            boolean blockStatus = InputMethods.getBoolean();
            user.setBlocked(blockStatus);
            System.out.println("Đã " + (blockStatus ? "Bỏ chặn" : "Chặn") + " quyền hoạt động của người dùng " + user.getUsername());
            IOFile.writeToFile(ShopConstants.USER_PATH, userDesign.getAll());
        } else {
            System.out.println("Không tìm thấy người dùng có ID người dùng là " + userIdInput);
        }
    }

}
