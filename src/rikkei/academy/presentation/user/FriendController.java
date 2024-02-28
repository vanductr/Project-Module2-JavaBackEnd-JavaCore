package rikkei.academy.presentation.user;

import rikkei.academy.business.design.IReportDesign;
import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.designImpl.ReportDesignImpl;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.entity.Report;
import rikkei.academy.business.entity.User;
import rikkei.academy.business.entity.UserRole;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopMessage;

import java.util.List;

public class FriendController {
    private static final IUserDesign userDesign = new UserDesignImpl();
    private static final IReportDesign reportDesign = new ReportDesignImpl();
    public static void displayFriendManagementMenu(String username) {
        User user = userDesign.findByUsername(username);
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                               ╔═══════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                               ║ " + "\u001B[33m" + user.getFullName() + "\u001B[32m -  QUẢN LÝ BẠN BÈ " + "\u001B[34m");
            System.out.println("║═════════════════════╝═══════════════════════════════╚═══════════════════════════════════════║");
            System.out.println("║ \u001B[36m                        1. HIỂN THỊ TẤT CẢ DANH SÁCH BẠN BÈ                                 \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        2. TÌM KIẾM BẠN BÈ THEO TÊN                                         \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        3. XEM DANH SÁCH TẤT CẢ USER VÀ THÊM MỚI BẠN BÈ                     \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        4. HIỂN THỊ DANH SÁCH GỢI Ý THEO BẠN CHUNG                          \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        5. THÊM MỚI BẠN BÈ BẰNG CÁCH TÌM KIẾM TÊN CỦA HỌ                    \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        6. LỜI MỜI KẾT BẠN TỪ NGƯỜI KHÁC                                    \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        7. DANH SÁCH CHẶN CỦA BẠN                                           \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        8. BÁO CÁO NGƯỜI DÙNG VI PHẠM                                       \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        0. QUAY LẠI MENU TRƯỚC                                              \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    displayAllFriend(username);
                    break;
                case 2:
                    findFriendByName(username);
                    break;
                case 3:
                    addNewFriend(username);
                    break;
                case 4:
                    suggestCommonFriendsListDisplay(username);
                    break;
                case 5:
                    addNewFriendByName(username);
                    break;
                case 6:
                    displayFriendRequestsMenu(username);
                    break;
                case 7:
                    displayBlockedUsers(username);
                    break;
                case 8:
                    reportUserViolation(username);
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void displayAllFriend(String username) {
        User isLoginUser = userDesign.findByUsername(username);
        if (isLoginUser.getFriends().isEmpty()) {
            System.out.println("Danh sách bạn bè đang trống.");
            return;
        }
        List<User> friendList = isLoginUser.getFriends();
        System.out.println("===== Danh sách bạn bè của bạn:");
        for (User user : friendList) {
            System.out.println(user.getFullName());
            System.out.println("----------------------------");
        }
        System.out.println("===== Bạn có muốn Huỷ bỏ Kết bạn với ai không?");
        System.out.println("1. Có");
        System.out.println("0. Không");
        System.out.print("Nhập lựa chọn: ");
        byte choice = InputMethods.getByte();
        switch (choice) {
            case 1:
                User friendFindById = null;
                while (true) {
                    System.out.print("Nhập Id của Bạn bè để Huỷ Kết bạn với họ: ");
                    String friendId = InputMethods.getString();
                    for (User user : friendList) {
                        if (user.getUserId().equals(friendId)) {
                            friendFindById = user;
                            break;
                        }
                    }
                    if (friendFindById == null) {
                        System.err.println(ShopMessage.NO_ID_ERROR);
                    } else {
                        break;
                    }
                }
                User userIsLogin = userDesign.findByUsername(username);
                List<User> friendOfUser = userIsLogin.getFriends();
                friendOfUser.remove(friendFindById);
                userIsLogin.setFriends(friendOfUser);
                userDesign.save(userIsLogin);
                System.out.println("Đã huỷ kết bạn với " + friendFindById.getFullName());
                break;
            case 0:
                return;
            default:
                System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
        }
    }

    private static void addNewFriend(String username) {
        List<User> userList = userDesign.getAll();
        User isLoginUser = userDesign.findByUsername(username);
        System.out.println("=========== Danh sách tất cả Người dùng hiện nay:");
        for (User user : userList) {
            if (!user.getUsername().equals(username)) {
                System.out.println(user.getUserId());
                System.out.println(user.getFullName());
                System.out.println("-------------------------------------");
            }
        }
        User user;
        while (true) {
            System.out.print("Nhập Id của họ để gửi Lời mời kết bạn đến Họ: ");
            String userIdInput = InputMethods.getString();
            user = userDesign.findById(userIdInput);
            if (user == null || user.getUsername().equals(username)) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                break;
            }
        }
        List<User> userRequestList = user.getFriendRequests();
        userRequestList.add(isLoginUser);
        user.setFriendRequests(userRequestList);
        userDesign.save(user);
        System.out.println("Đã gửi lời mời kết bạn đến " + user.getFullName() + ". Hãy chờ họ chấp nhận.");
    }

    private static void displayFriendRequestsMenu(String username) {
        User userIsLogin = userDesign.findByUsername(username);
        if (userIsLogin.getFriendRequests().isEmpty()) {
            System.out.println("===== Danh sách lời mời kết bạn của bạn đang trống.");
            return;
        }
        List<User> friendRequests = userIsLogin.getFriendRequests();
        System.out.println("===== Danh sách lời mời kết bạn:");
        for (User friendRequest : friendRequests) {
            System.out.println(friendRequest.getUserId());
            System.out.println(friendRequest.getFullName());
            System.out.println("--------------------------------");
        }
        User friend;
        while (true) {
            System.out.print("Nhập Id của họ để Đồng ý kết bạn: ");
            String friendId = InputMethods.getString();
            friend = userDesign.findById(friendId);
            if (friend == null) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                friendRequests.remove(friend);
                break;
            }
        }
        List<User> friends = userIsLogin.getFriends();
        List<User> friends2 = friend.getFriends();
        friends.add(friend);
        friends2.add(userIsLogin);
        userIsLogin.setFriends(friends);
        friend.setFriends(friends2);
        userDesign.save(userIsLogin);
        userDesign.save(friend);
        System.out.println("Đã thêm " + friend.getFullName() + " vào danh sách bạn bè.");
    }

    private static void findFriendByName(String username) {
        User userIsLogin = userDesign.findByUsername(username);
        List<User> friendList = userIsLogin.getFriends();
        if (friendList.isEmpty()) {
            System.out.println("====== Danh sách bạn bè của bạn đang trống.");
            return;
        }
        User friendFindByName;
        String userNameInput;
        while (true) {
            System.out.print("Nhập tên(username) để tìm bạn bè: ");
            userNameInput = InputMethods.getString();
            friendFindByName = userDesign.findByUsername(userNameInput);
            if (friendFindByName == null) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                break;
            }
        }
        System.out.println("Đã tìm thấy người bạn có tên(username) " + userNameInput + " :");
        System.out.println("username: " + friendFindByName.getUsername());
        System.out.println("fule name: " + friendFindByName.getFullName());
    }

    private static void addNewFriendByName(String username) {
        System.out.print("Nhập tên(username) để xem và gửi lời mời kết bạn: ");
        String userNameInput = InputMethods.getString();
        User userFindByName = userDesign.findByUsername(userNameInput);
        if (userFindByName == null) {
            System.err.println(ShopMessage.NO_NAME_ERROR);
            return;
        }
        System.out.println("===== Đây là người bạn muốn tìm:");
        System.out.println("username: " + userFindByName.getUsername());
        System.out.println("fule name: " + userFindByName.getFullName());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        while (true) {
            System.out.print("Nhập 1 để thêm bạn, 0 để thoát(không thêm bạn): ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                List<User> friendRequests = userFindByName.getFriendRequests();
                friendRequests.add(userDesign.findByUsername(username));
                userFindByName.setFriendRequests(friendRequests);
                userDesign.save(userFindByName);
                System.out.println("Đã gửi lời mời kết bạn đến " + userFindByName.getFullName() + ". Hãy chờ họ đồng ý.");
                break;
            } else if (choice == 0) {
                break;
            } else {
                System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void displayBlockedUsers(String username) {
        User isLoginUser = userDesign.findByUsername(username);
        if (isLoginUser.getBlockedUsers().isEmpty()) {
            System.out.println("Danh sách chặn đang trống.");
            return;
        }
        List<User> blockedList = isLoginUser.getBlockedUsers();
        System.out.println("===== Danh sách chặn của bạn:");
        for (User user : blockedList) {
            System.out.println(user.getUserId());
            System.out.println(user.getFullName());
            System.out.println("----------------------------");
        }
        System.out.println("===== Bạn có muốn bỏ chặn ai không?");
        System.out.println("1. Giữ nguyên Danh sách Chặn này");
        System.out.println("2. Bỏ chặn cho 1 người");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    System.out.println("====== Danh sách chặn vẫn được giữ nguyên.");
                    break;
                case 2:
                    User userFindById;
                    while (true) {
                        System.out.print("Nhập Id của họ để bỏ chặn: ");
                        String userIdInput = InputMethods.getString();
                        userFindById = userDesign.findById(userIdInput);
                        if (userFindById == null) {
                            System.err.println(ShopMessage.NO_ID_ERROR);
                        } else {
                            break;
                        }
                    }
                    blockedList.remove(userFindById);
                    System.out.println("======= Đã bỏ chặn " + userFindById.getFullName());
                    break;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void suggestCommonFriendsListDisplay(String username) {
        User userIsLogin = userDesign.findByUsername(username);
        List<User> friendList = userIsLogin.getFriends();
        for (User user : friendList) {
            System.out.println("===== Danh sách Người dùng có bạn chung với " + user.getFullName() + ": " );
            List<User> friendsOfFriend = user.getFriends();
            boolean flag = false;
            for (User user1 : friendsOfFriend) {
                if (!user1.getUserId().equals(userIsLogin.getUserId())) {
                    flag = false;
                } else {
                    System.out.println("User ID: " + user1.getUserId());
                    System.out.println("UserName: " + user1.getUsername());
                    System.out.println("------------------------------------");
                    flag = true;
                }
            }
            if (!flag) {
                System.out.println("===== Hiện tại danh sách của " + user.getFullName() + " đang trống");
            }
        }
    }

    private static void reportUserViolation(String username) {
        User userIsLogin = userDesign.findByUsername(username);
        User subjectToReport;
        while (true) {
            System.out.print("══════ NHẬP ID CỦA NGƯỜI DÙNG ĐỂ BÁO CÁO HỌ VI PHẠM: ");
            String userId = InputMethods.getString();
            subjectToReport = userDesign.findById(userId);
            if (subjectToReport == null || subjectToReport.getUserId().equals(userIsLogin.getUserId()) || subjectToReport.getRole() == UserRole.ADMIN) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                break;
            }
        }
        System.out.println("═══════ ĐÂY LÀ THÔNG TIN CỦA ĐỐI TƯỢNG MUỐN BÁO CÁO:");
        System.out.println("User Id: " + subjectToReport.getUserId());
        System.out.println("UserName: " + subjectToReport.getUsername());
        System.out.println("Full Name: " + subjectToReport.getFullName());
        System.out.println("─────────────────────────────────────────────────────────────────────────────────");
        System.out.println("1. Báo cáo người này");
        System.out.println("0. Không báo cáo (Thoát)");
        while (true) {
            System.out.print("═══ Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    Report report = new Report();
                    List<Report> reportList = subjectToReport.getReportList();
                    report.setReportId(reportDesign.getNewId());
                    report.inputDataReport();
                    report.setReporterUser(userIsLogin);
                    reportDesign.save(report);
                    reportList.add(report);
                    subjectToReport.setReportList(reportList);
                    userDesign.save(subjectToReport);
                    System.out.println("══════ BẠN VỪA BÁO CÁO " + subjectToReport.getFullName());
                    return;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }
}
