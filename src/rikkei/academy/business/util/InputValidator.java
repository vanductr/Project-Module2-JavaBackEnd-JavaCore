package rikkei.academy.business.util;

import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.entity.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    private static final IUserDesign userDesign = new UserDesignImpl();
    public static boolean isValidUsername(String username) {
        // Kiểm tra độ dài
        if (username.length() <= 8) {
            return false;
        }

        // Kiểm tra ký tự đặc biệt
        if (!username.matches("[a-zA-Z0-9]+")) {
            return false;
        }

        // Kiểm tra trùng lặp với dữ liệu đã đăng ký
        List<User> userList = userDesign.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidEmail(String email) {
        // Biểu thức chính quy cho một địa chỉ email hợp lệ
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Tạo một đối tượng Pattern từ biểu thức chính quy
        Pattern pattern = Pattern.compile(emailRegex);

        // Tạo một đối tượng Matcher để so khớp địa chỉ email với biểu thức chính quy
        Matcher matcher = pattern.matcher(email);

        // Kiểm tra xem email có khớp với biểu thức chính quy không
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        // Biểu thức chính quy cho một mật khẩu hợp lệ
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        // Tạo một đối tượng Pattern từ biểu thức chính quy
        Pattern pattern = Pattern.compile(passwordRegex);

        // Tạo một đối tượng Matcher để so khớp mật khẩu với biểu thức chính quy
        Matcher matcher = pattern.matcher(password);

        // Kiểm tra xem mật khẩu có khớp với biểu thức chính quy không
        return matcher.matches();
    }

    public static boolean isValidFullName(String fullName) {
        // Kiểm tra xem Họ và Tên có được cách nhau bằng dấu cách không
        return fullName.matches("^\\p{L}+\\s+\\p{L}+(\\s+\\p{L}+)*$");
    }
}
