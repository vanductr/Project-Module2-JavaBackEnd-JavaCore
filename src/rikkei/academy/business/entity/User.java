package rikkei.academy.business.entity;

import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.InputValidator;
import rikkei.academy.business.util.ShopMessage;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private LocalDate dateOfBirth;
    private LocalDateTime registrationDate;
//    private UserRole role = UserRole.ADMIN;
    private UserRole role = UserRole.USER;
    private boolean blocked = true;
    private List<User> friends = new ArrayList<>();
    private List<User> friendRequests = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<User> blockedUsers = new ArrayList<>();
    private List<Group> invitationsToJoinGroups = new ArrayList<>();
    private List<Report> reportList = new ArrayList<>();

    public User() {
    }

    public User(String userId, String username, String email, String password, String fullName, LocalDate dateOfBirth, LocalDateTime registrationDate) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = registrationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<User> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(List<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public List<Group> getInvitationsToJoinGroups() {
        return invitationsToJoinGroups;
    }

    public void setInvitationsToJoinGroups(List<Group> invitationsToJoinGroups) {
        this.invitationsToJoinGroups = invitationsToJoinGroups;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public void inputData() {
        while (true) {
            System.out.print("Tên đăng nhập (Lớn hơn 8 ký tự và không có ký tự đặc biệt): ");
            String username = InputMethods.getString();
            if (InputValidator.isValidUsername(username)) {
                this.username = username;
                break;
            } else {
                System.err.println(ShopMessage.NO_USERNAME_ERROR);
            }
        }

        while (true) {
            System.out.print("Nhập Email (Email phải theo Định dạng: example@example.com): ");
            String email = InputMethods.getString();
            if (InputValidator.isValidEmail(email)) {
                this.email = email;
                break;
            } else {
                System.err.println(ShopMessage.EMAIL_ERROR);
            }
        }

        String password1;
        while (true) {
            System.out.print("Mật khẩu(phải trên 8 ký tự, có chứa ký tự in hoa và số, và có 1 ký tự đặc biệt): ");
            password1 = InputMethods.getString();
            if (InputValidator.isValidPassword(password1)) {
                break;
            } else {
                System.err.println(ShopMessage.PASSWORD1_ERROR);
            }
        }

        while (true) {
            System.out.print("Nhập lại Mật khẩu(Để xác nhận): ");
            String password2 = InputMethods.getString();
            if (password2.equals(password1)) {
                this.password = password2;
                break;
            } else {
                System.err.println(ShopMessage.PASSWORD2_ERROR);
            }
        }

        while (true) {
            System.out.print("Họ và tên (Họ và Tên phải được cách nhau bằng dấu Cách): ");
            String fullName = InputMethods.getString();
            if (InputValidator.isValidFullName(fullName)) {
                this.fullName = fullName;
                break;
            } else {
                System.err.println(ShopMessage.FULLNAME_ERROR);
            }
        }

        System.out.print("Ngày sinh (yyyy-MM-dd): ");
        String dobString = InputMethods.getString();
        this.dateOfBirth = LocalDate.parse(dobString);

        // Giả sử registrationDate được đặt là ngày hiện tại
        this.registrationDate = LocalDateTime.now();
    }

    public void displayData() {
        System.out.println("\u001B[34m╔══════════════════════════════ THÔNG TIN CÁ NHÂN ═════════════════════════════════╗");
        System.out.println("\u001B[34m║ \u001B[37mID người dùng: \u001B[37m" + userId + " ".repeat(57 - userId.length()) + "         \u001B[34m║");
        System.out.println("\u001B[34m║ \u001B[37mTên người dùng: \u001B[37m" + username + " ".repeat(54 - username.length()) + "           \u001B[34m║");
        System.out.println("\u001B[34m║ \u001B[37mEmail: \u001B[37m" + email + " ".repeat(61 - email.length()) + "             \u001B[34m║");
        System.out.println("\u001B[34m║ \u001B[37mHọ và tên: \u001B[37m" + fullName + " ".repeat(57 - fullName.length()) + "             \u001B[34m║");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("\u001B[34m║ \u001B[37mNgày sinh: \u001B[37m" + dateOfBirth.format(formatter) + " ".repeat(55 - dateOfBirth.format(formatter).length()) + "               \u001B[34m║");
        System.out.println("\u001B[34m║ \u001B[37mNgày đăng ký: \u001B[37m" + registrationDate.format(formatter) + " ".repeat(53 - registrationDate.format(formatter).length()) + "              \u001B[34m║");

        if (friends != null && !friends.isEmpty()) {
            System.out.println("\u001B[34m║ \u001B[37mDanh sách bạn bè:");
            for (User friend : friends) {
                System.out.println("\u001B[34m║   \u001B[37m- " + friend.getUsername() + " ".repeat(62 - friend.getUsername().length()) + "\u001B[34m║");
            }
        } else {
            System.out.println("\u001B[34m║ \u001B[37mHiện tại danh sách bạn bè đang trống." + " ".repeat(44) + "\u001B[34m║");
        }

        if (groups != null && !groups.isEmpty()) {
            System.out.println("\u001B[34m║ \u001B[37mDanh sách Groups:");
            for (Group group : groups) {
                System.out.println("\u001B[34m║   \u001B[37m- " + group.getGroupName() + ": " + group.getDescription() + " ".repeat(48 - group.getGroupName().length() - group.getDescription().length()) + "\u001B[34m║");
            }
        } else {
            System.out.println("\u001B[34m║ \u001B[37mHiện tại danh sách nhóm đang trống." + " ".repeat(46) + "\u001B[34m║");
        }
        System.out.println("\u001B[34m╚══════════════════════════════════════════════════════════════════════════════════╝");
    }


}

