package rikkei.academy.business.design;

import rikkei.academy.business.entity.User;
import rikkei.academy.business.entity.UserRole;

public interface IUserDesign extends IGenericDesign<User, String> {
    User findByUsername(String username);
    void register(User user);
    UserRole login(String username, String password);
}

