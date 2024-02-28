package rikkei.academy.business.designImpl;

import rikkei.academy.business.entity.User;
import rikkei.academy.business.entity.UserRole;
import rikkei.academy.business.design.IUserDesign;
import rikkei.academy.business.util.IOFile;
import rikkei.academy.business.util.ShopConstants;

import java.util.List;

public class UserDesignImpl implements IUserDesign {
    private static List<User> userList;

    public UserDesignImpl() {
        userList = IOFile.readFromFile(ShopConstants.USER_PATH);
    }

    @Override
    public List<User> getAll() {
        return userList;
    }

    @Override
    public void save(User user) {
        if (findById(user.getUserId()) == null) {
            userList.add(user);
        } else {
            userList.set(userList.indexOf(findById(user.getUserId())), user);
        }
        IOFile.writeToFile(ShopConstants.USER_PATH, userList);
    }

    @Override
    public User findById(String id) {
        for (User user : userList) {
            if (user.getUserId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(User user) {
        userList.remove(user);
        IOFile.writeToFile(ShopConstants.USER_PATH, userList);
    }

    @Override
    public String getNewId() {
        int idMax = 0;
        for (User user : userList) {
            int userId = Integer.parseInt(user.getUserId().replace("U", "0"));
            if (idMax < userId) {
                idMax = userId;
            }
        }
        idMax += 1;
        return "U" + String.format("%03d", idMax);
    }

    @Override
    public void register(User user) {
        userList.add(user);
        IOFile.writeToFile(ShopConstants.USER_PATH, userList);
        System.out.println("Đăng ký tài khoản thành công!");
    }

    @Override
    public UserRole login(String username, String password) {
        User user = findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user.getRole();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}

