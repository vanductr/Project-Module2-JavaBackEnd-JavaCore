package rikkei.academy.presentation.admin;

import rikkei.academy.business.design.IGroupDesign;
import rikkei.academy.business.design.IReportDesign;
import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.designImpl.GroupDesignImpl;
import rikkei.academy.business.designImpl.ReportDesignImpl;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.entity.Group;
import rikkei.academy.business.entity.Report;
import rikkei.academy.business.entity.User;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AdminGroupController {
    private static final IGroupDesign groupDesign = new GroupDesignImpl();
    private static final IUserDesign userDesign = new UserDesignImpl();
    private static final IReportDesign reportDesign = new ReportDesignImpl();
    public static void showAdminGroupManagementMenu() {
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                     ╔═════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                     ║   " + "\u001B[32m ADMIN  " + "\u001B[33m" + "  QUẢN LÝ GROUP" + "\u001B[34m           ║");
            System.out.println("║═════════════════════╝═════════════════════╚═════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       1. HIỂN THỊ TẤT CẢ CÁC GROUP HIỆN TẠI                    \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       2. TÌM KIẾM GROUP THEO TÊN                               \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       3. TÌM KIẾM GROUP THEO TÊN TRƯỞNG NHÓM                   \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       4. XEM CÁC BÁO CÁO VI PHẠM TỪ NGƯỜI DÙNG ĐẾN GROUP       \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       5. SẮP XẾP GROUP THEO SỐ LƯỢNG THÀNH VIÊN GIẢM DẦN       \u001B[34m║");
            System.out.println("║═════════════════════════════════════════════════════════════════════════════════║");
            System.out.println("║ \u001B[36m                       0. QUAY LẠI MENU TRƯỚC                                   \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    displayAllGroup();
                    break;
                case 2:
                    searchGroupByName();
                    break;
                case 3:
                    searchGroupByLeaderName();
                    break;
                case 4:
                    showReportsFromUsersToGroups();
                    break;
                case 5:
                    sortGroupByMemberCountDescending();
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void displayAllGroup() {
        List<Group> groupList = groupDesign.getAll();
        System.out.println("===== Danh sách tất cả Group:");
        for (Group group : groupList) {
            group.displayDataGroup();
            System.out.println("---------------------------------");
        }
    }

    private static void searchGroupByName() {
        List<Group> groupList = groupDesign.getAll();
        System.out.print("Nhập tên Group muốn tìm kiếm: ");
        String groupName = InputMethods.getString();
        List<Group> groupFindByNames = new ArrayList<>();
        for (Group group : groupList) {
            if (group.getGroupName().equals(groupName)) {
                groupFindByNames.add(group);
            }
        }
        if (groupFindByNames.isEmpty()) {
            System.err.println(ShopMessage.NO_NAME_ERROR);
            return;
        }
        System.out.println("===== Danh sách Group tìm được theo tên: " + groupName + " là:");
        for (Group groupFindByName : groupFindByNames) {
            groupFindByName.displayDataGroup();
            System.out.println("------------------------------------");
        }
    }

    private static void searchGroupByLeaderName() {
        List<Group> groupList = groupDesign.getAll();
        List<Group> groups = new ArrayList<>();
        System.out.print("Nhập Tên Trưởng nhóm để tìm Các Group của họ: ");
        String leaderName = InputMethods.getString();
        User leader = userDesign.findByUsername(leaderName);
        if (leader == null) {
            System.err.println(ShopMessage.NO_NAME_ERROR);
            return;
        }
        for (Group group : groupList) {
            if (group.getLeader().getUsername().equals(leaderName)) {
                groups.add(group);
            }
        }
        if (groups.isEmpty()) {
            System.out.println(leaderName + " hiện tại đang không là Trưởng nhóm của Nhóm nào.");
            return;
        }
        System.out.println("====== Danh sách các Group mà " + leaderName + " là Trưởng nhóm:");
        for (Group group : groups) {
            group.inputDataGroup();
            System.out.println("----------------------------------");
        }
    }

    private static void showReportsFromUsersToGroups() {
        List<Group> groupList = groupDesign.getAll();
        List<Group> groupsReposted = new ArrayList<>();
        for (Group group : groupList) {
            List<Report> reportList = group.getReports();
            if (!reportList.isEmpty()) {
                groupsReposted.add(group);
            }
        }
        if (groupsReposted.isEmpty()) {
            System.out.println("===== Hiện tại không có Group nào bị Người dùng báo cáo");
            return;
        }
        System.out.println("===== Danh sách Các Group bị người dùng báo cáo:");
        for (Group group : groupsReposted) {
            group.displayDataGroup();
            System.out.println("----------------------------------------");
        }
        System.out.println("Bạn có muốn Xem nội dung báo cáo và xử lý chúng không ?");
        System.out.println("1. Có");
        System.out.println("0. Không");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    handleViolationReports(groupsReposted);
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }
    private static void handleViolationReports(List<Group> groupsReposted) {
        Group groupFindById = null;
        while (true) {
            System.out.print("Nhập Id của Group để Xem report và Xử lý chúng: ");
            String groupId = InputMethods.getString();
            for (Group group : groupsReposted) {
                if (group.getGroupId().equals(groupId)) {
                    groupFindById = group;
                    break;
                }
            }
            if (groupFindById == null) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                break;
            }
        }
        System.out.println("===== Đây là Group bị Báo cáo:");
        groupFindById.displayDataGroup();
        System.out.println("═════════════════════════════════════════════════════════════════════════");
        System.out.println("===== Đây là Những Báo cáo chưa được xử lý:");
        List<Report> reportList = groupFindById.getReports();
        for (Report report : reportList) {
            if (!report.getStatus()) {
                report.displayData();
            }
            System.out.println("--------------------------------------------");
        }
        System.out.println("======= Xử lý Báo cáo và Thay đổi trạng thái hoạt động của Group =======");
        System.out.println("1. Nội dung Báo cáo không hợp lý (Vu khống)");
        System.out.println("2. Báo Cáo Đúng, Phát hiện vi phạm của Nhóm này");
        System.out.println("0. Thoát xử lý");
        System.out.print("Nhập lựa chọn: ");
        byte choice = InputMethods.getByte();
        switch (choice) {
            case 1:
                Report reportFindById = null;
                while (true) {
                    System.out.print("Nhập Id Báo cáo để Xử lý: ");
                    String reportId = InputMethods.getString();
                    for (Report report : reportList) {
                        if (report.getReportId().equals(reportId)) {
                            reportFindById = report;
                        }
                    }
                    if (reportFindById == null) {
                        System.err.println(ShopMessage.NO_ID_ERROR);
                    } else {
                        break;
                    }
                }
                reportFindById.setStatus(true);
                reportFindById.setResolvedDate(LocalDateTime.now());
                reportDesign.save(reportFindById);
                System.out.println("===== Đã xử lý một Báo cáo.");
                break;
            case 2:
                groupFindById.setStatus(false);
                for (Report report : reportList) {
                    report.setStatus(true);
                    report.setResolvedDate(LocalDateTime.now());
                    reportDesign.save(report);
                }
                groupDesign.save(groupFindById);
                System.out.println("Đã xử lý toàn bộ Báo cáo và Tạm thời Chặn hoạt động nhóm này.");
                break;
            case 0:
                return;
            default:
                System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
        }
    }

    private static void sortGroupByMemberCountDescending() {
        List<Group> groupList = groupDesign.getAll();
        groupList.sort(Comparator.comparingInt(group -> -group.getMember().size()));
        System.out.println("===== Danh sách Các Group được sắp xếp theo Member:");
        for (Group group : groupList) {
            System.out.println(group);
        }
    }
}
