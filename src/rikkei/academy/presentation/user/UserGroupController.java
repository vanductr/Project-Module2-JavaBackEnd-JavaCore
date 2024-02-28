package rikkei.academy.presentation.user;

import rikkei.academy.business.design.IGroupDesign;
import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.designImpl.GroupDesignImpl;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.entity.Group;
import rikkei.academy.business.entity.User;
import rikkei.academy.business.entity.UserRole;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopMessage;

import java.util.ArrayList;
import java.util.List;

public class UserGroupController {
    private static final IUserDesign userDesign = new UserDesignImpl();
    private static final IGroupDesign groupDesign = new GroupDesignImpl();
    public static void displayUserGroupMenu(String username) {
        User userIsLogin = userDesign.findByUsername(username);
        while (true) {
            System.out.println("\u001B[34m╔═════════════════════╗" + "                               ╔═══════════════════════════════════════╗");
            System.out.println("║ \u001B[36m    FAKEBOOK \u001B[34m       ║" + "                               ║ " + "\u001B[33m" + userIsLogin.getFullName() + "\u001B[32m -  QUẢN LÝ GROUP  " + "\u001B[34m");
            System.out.println("║═════════════════════╝═══════════════════════════════╚═══════════════════════════════════════║");
            System.out.println("║ \u001B[36m                        1. HIỂN THỊ CÁC GROUP MÀ BẠN LÀ THÀNH VIÊN                          \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        2. HIỂN THỊ TẤT CẢ CÁC GROUP HIỆN CÓ                                \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        3. TÌM KIẾM GROUP THEO TÊN                                          \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        4. TẠO MỚI MỘT GROUP                                                \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        5. HIỂN THỊ CÁC NHÓM MÀ BẠN LÀ TRƯỞNG NHÓM                          \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        6. QUẢN LÝ GROUP MÀ BẠN LÀ TRƯỞNG NHÓM (BẰNG ID NHÓM)               \u001B[34m║");
            System.out.println("║─────────────────────────────────────────────────────────────────────────────────────────────║");
            System.out.println("║ \u001B[36m                        0. QUAY LẠI MENU TRƯỚC                                              \u001B[34m║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    displayMemberGroups(userIsLogin);
                    break;
                case 2:
                    showAllGroup(userIsLogin);
                    break;
                case 3:
                    searchGroupByName();
                    break;
                case 4:
                    createNewGroup(userIsLogin);
                    break;
                case 5:
                    displayOwnedGroups(userIsLogin);
                    break;
                case 6:
                    manageOwnedGroups(userIsLogin);
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void displayMemberGroups(User user) {
        List<Group> groupList = groupDesign.getAll();
        List<Group> groupOfUser = new ArrayList<>();
        for (Group group : groupList) {
            List<User> members = group.getMember();
            for (User member : members) {
                if (member.getUserId().equals(user.getUserId()) && !group.getLeader().getUserId().equals(user.getUserId())) {
                    groupOfUser.add(group);
                }
            }
        }
        if (groupOfUser.isEmpty()) {
            System.out.println("Hiện tại bạn chưa tham gia bất kì Group nào");
            return;
        }
        System.out.println("====== Danh sách các Group mà bạn đang là thành viên: ");
        for (Group group : groupOfUser) {
            group.displayDataGroup();
            System.out.println("--------------------------------");
        }
        System.out.println("===== Bạn có muốn rời khỏi Group nào không ?");
        System.out.println("1. Có");
        System.out.println("0. Không");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    leaveGroup(groupOfUser, user);
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }
    private static void leaveGroup(List<Group> groupOfUser, User user) {
        Group group;
        while (true) {
            System.out.print("Nhập Id của Group để rời khỏi nhóm đó: ");
            String groupId = InputMethods.getString();
            group = groupDesign.findById(groupId);
            if (group == null) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                boolean is = false;
                for (Group group1 : groupOfUser) {
                    if (group1.getGroupId().equals(group.getGroupId())) {
                        is = true;
                        break;
                    }
                }
                if (is) {
                    break;
                } else {
                    System.err.println(ShopMessage.NO_ID_ERROR);
                }
            }
        }
        List<User> members = group.getMember();
        members.remove(user);
        group.setMember(members);
        groupDesign.save(group);
        System.out.println("===== Bạn vừa rời khỏi nhóm " + group.getGroupName());
    }

    private static void showAllGroup(User user) {
        List<Group> groupList = groupDesign.getAll();
        if (groupList.isEmpty()) {
            System.out.println("===== Danh sách Group hiện tại đang trống.");
            return;
        }
        System.out.println("===== Danh sách các group hiện tại:");
        for (Group group : groupList) {
            group.displayDataGroup();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        System.out.println("===== Bạn có muốn gửi lời mời tham gia Group nào không?");
        System.out.println("1. Có");
        System.out.println("0. Không");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    requestToJoinGroup(user);
                    return;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }

    }

    private static void searchGroupByName() {
        List<Group> groupList = groupDesign.getAll();
        System.out.print("Nhập tên Group mà bạn muốn tìm kiếm: ");
        String groupName = InputMethods.getString();
        List<Group> groups = new ArrayList<>();
        for (Group g : groupList) {
            if (g.getGroupName().equals(groupName)) {
                groups.add(g);
            }
        }
        if (groups.isEmpty()) {
            System.err.println(ShopMessage.NO_NAME_ERROR);
            return;
        }
        System.out.println("===== Danh sách các Nhóm có tên " + groupName + ": ");
        for (Group group : groups) {
            group.displayDataGroup();
            System.out.println("---------------------------------------");
        }
    }

    private static void requestToJoinGroup(User user) {
        Group group;
        while (true) {
            System.out.print("Nhập Id của Group để gửi lời tham gia: ");
            String groupId = InputMethods.getString();
            group = groupDesign.findById(groupId);
            if (group == null) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                break;
            }
        }
        List<User> requestList = group.getRequest();
        requestList.add(user);
        group.setRequest(requestList);
        groupDesign.save(group);
        System.out.println("Đã gửi yêu cầu gia nhập đến Nhóm: " + group.getGroupName() + ". Hãy đợi Trưởng nhóm chấp nhận.");
    }

    private static void createNewGroup(User user) {
        Group group = new Group();
        group.setGroupId(groupDesign.getNewId());
        group.inputDataGroup();
        group.setLeader(user);
        List<User> members = group.getMember();
        members.add(user);
        group.setMember(members);
        groupDesign.save(group);
        System.out.println("Đã tạo thành công 1 Group mới, bạn là trưởng nhóm.");
    }

    private static void displayOwnedGroups(User userIsLogin) {
        List<Group> groupList = groupDesign.getAll();
        List<Group> groups = new ArrayList<>();
        for (Group group : groupList) {
            if (group.getLeader().getUserId().equals(userIsLogin.getUserId())) {
                groups.add(group);
            }
        }
        if (groups.isEmpty()) {
            System.out.println("===== Hiện tại bạn không là trưởng nhóm của nhóm nào.");
            return;
        }
        System.out.println("===== Danh sách các nhóm mà bạn là trưởng nhóm:");
        for (Group group : groups) {
            group.displayDataGroup();
            System.out.println("------------------------------------");
        }
    }

    private static void manageOwnedGroups(User userIsLogin) {
        Group group;
        while (true) {
            System.out.print("Nhập Id của Group để quản lý nhóm: ");
            String groupIdInput = InputMethods.getString();
            group = groupDesign.findById(groupIdInput);
            if (group == null) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                if (group.getLeader().getUserId().equals(userIsLogin.getUserId())) {
                    break;
                } else {
                    System.err.println("==== Hiện tại bạn đang không là Trưởng nhóm của Group nào, hoặc Id nhập vào không đúng!");
                    while (true) {
                        System.out.print("Nhập 1 để nhập lại Id, 0 để thoát: ");
                        byte choice = InputMethods.getByte();
                        if (choice == 0) {
                            return;
                        } else if (choice == 1) {
                            break;
                        } else {
                            System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
                        }
                    }
                }
            }
        }

        while (true) {
            System.out.println("=========== QUẢN LÝ NHÓM CỦA " + userIsLogin.getFullName() + " =====================");
            System.out.println("1. Xem tất cả các thành viên trong nhóm");
            System.out.println("2. Huỷ tư cách thành viên(đuổi khỏi nhóm) một thành viên");
            System.out.println("3. Hiển thị tất cả các lời yêu cầu tham gia nhóm");
            System.out.println("4. Mời người dùng tham gia nhóm của bạn");
            System.out.println("5. Chỉnh sửa thông tin nhóm");
            System.out.println("6. Xoá nhóm này");
            System.out.println("0. Quay lại Menu trước");
            System.out.print("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    List<User> members = group.getMember();
                    System.out.println("===== Danh sách tất cả các Thành viên trong nhóm:");
                    for (User member : members) {
                        System.out.println("Id người dùng: " + member.getUserId());
                        System.out.println("User Name: " + member.getUsername());
                        if (group.getLeader().getUserId().equals(member.getUserId())) {
                            System.out.println("Chức vụ: Trưởng nhóm");
                        } else {
                            System.out.println("Chức vụ: Thành viên");
                        }
                        System.out.println("------------------------------");
                    }
                    break;
                case 2:
                    removeMemberFromGroupMenu(group, userIsLogin);
                    break;
                case 3:
                    displayJoinRequests(group);
                    break;
                case 4:
                    inviteUsersToGroup(group);
                    break;
                case 5:
                    editGroupInformation(group);
                    break;
                case 6:
                    groupDesign.delete(group);
                    System.out.println("===== Bạn đã xoá Group này.");
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void removeMemberFromGroupMenu(Group group, User userIsLogin) {
        while (true) {
            System.out.print("Nhập Id của thành viên để đuổi họ khỏi nhóm: ");
            String memberId = InputMethods.getString();
            List<User> members = group.getMember();
            User member = userDesign.findById(memberId);
            if (member == null || member.getUserId().equals(userIsLogin.getUserId())) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                boolean flag = false;
                for (User m : members) {
                    if (m.getUserId().equals(memberId)) {
                        members.remove(m);
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    groupDesign.save(group);
                    System.out.println("===== Đã xoá thành công 1 member.");
                    break;
                } else {
                    System.err.println("===== Người này không trong nhóm của bạn!!! Hãy kiểm tra lại.");
                }
            }
        }
    }

    private static void displayJoinRequests(Group group) {
        List<User> requests = group.getRequest();
        List<User> members = group.getMember();
        if (requests.isEmpty()) {
            System.out.println("===== Danh sách Yêu cầu tham gia nhóm Đang trống.");
            return;
        }
        System.out.println("===== Danh sách người dùng gửi lời yêu cầu tham gia nhóm: ");
        for (User request : requests) {
            System.out.println("User Id: " + request.getUserId());
            System.out.println("User Name: " + request.getUsername());
            System.out.println("Họ và Tên: " + request.getFullName());
            System.out.println("-----------------------------");
        }
        System.out.println("===== Bạn có muốn đồng ý cho ai vào nhóm không?");
        System.out.println("1. Có (Chấp nhận 1 người)");
        System.out.println("2. Chấp nhận tất cả");
        System.out.println("0. Không");
        while (true) {
            System.out.print("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    User user;
                    while (true) {
                        System.out.print("Nhập Id của người dùng để thêm họ vào nhóm: ");
                        String userId = InputMethods.getString();
                        user = userDesign.findById(userId);
                        if (user == null ) {
                            System.err.println(ShopMessage.NO_ID_ERROR);
                        } else {
                            boolean f = false;
                            for (User request : requests) {
                                if (request.getUserId().equals(userId)) {
                                    user = request;
                                    f = true;
                                    break;
                                }
                            }
                            if (f) {
                                boolean flag = false;
                                for (User member : members) {
                                    if (member.getUserId().equals(user.getUserId())) {
                                        System.err.println("===== Người dùng này đã là thành viên của nhóm.");
                                        flag = true;
                                        break;
                                    }
                                }
                                if (!flag) {
                                    break;
                                }
                            } else {
                                System.err.println("===== Người này không trong Danh sách Gửi yêu cầu tham gia nhóm.");
                            }
                        }
                    }
                    members.add(user);
                    requests.remove(user);
                    group.setMember(members);
                    groupDesign.save(group);
                    System.out.println("===== Đã thêm " + user.getFullName() + " vào nhóm.");
                    break;
                case 2:
                    members.addAll(requests);
                    requests.clear();
                    groupDesign.save(group);
                    System.out.println("===== Đã đồng ý tất cả yêu cầu tham gia nhóm.");
                    break;
                case 0:
                    return;
                default:
                    System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }

    private static void inviteUsersToGroup(Group group) {
        List<User> userList = userDesign.getAll();
        List<User> members = group.getMember();
        List<User> users = new ArrayList<>();
        System.out.println("===== Danh sách tất cả người dùng chưa tham gia nhóm của bạn:");
        for (User user : userList) {
            boolean isMember = false;
            for (User member : members) {
                if (user.getUserId().equals(member.getUserId())) {
                    isMember = true;
                    break;
                }
            }
            if (!isMember) {
                users.add(user);
            }
        }
        for (User user : users) {
            if (user.getRole() == UserRole.USER) {
                System.out.println("Id người dùng: " + user.getUserId());
                System.out.println("UserName: " + user.getUsername());
                System.out.println("Họ và Tên: " + user.getFullName());
                System.out.println("----------------------------");
            }
        }
        User user = null;
        while (true) {
            System.out.print("Nhập ID người dùng để mời họ vào nhóm: ");
            String userId = InputMethods.getString();
            for (User user1 : users) {
                if (user1.getUserId().equals(userId)) {
                    user = user1;
                    break;
                }
            }
            if (user == null || user.getRole() == UserRole.ADMIN) {
                System.err.println(ShopMessage.NO_ID_ERROR);
            } else {
                List<Group> groups = user.getInvitationsToJoinGroups();
                boolean isInvitation = false;
                for (Group group1 : groups) {
                    if (group1.getGroupId().equals(group.getGroupId())) {
                        System.out.println("===== Đã gửi lời mời tham gia đến: " + user.getFullName() + " rồi. Không thể gửi thêm nữa.");
                        isInvitation = true;
                        break;
                    }
                }
                if (!isInvitation) {
                    break;
                }
            }
        }
        List<Group> invitationsToJoinGroups = user.getInvitationsToJoinGroups();
        invitationsToJoinGroups.add(group);
        user.setInvitationsToJoinGroups(invitationsToJoinGroups);
        userDesign.save(user);
        System.out.println("===== Đã Gửi lời mời tham gia nhóm đến " + user.getFullName());
    }

    private static void editGroupInformation(Group group) {
        System.out.println("==== Chỉnh sửa thông tin Group =====");
        group.inputDataGroup();
        groupDesign.save(group);
        System.out.println("====== Đã chỉnh sửa thành công thông tin Group của bạn.");
    }
}
