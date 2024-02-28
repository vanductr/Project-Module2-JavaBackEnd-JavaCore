package rikkei.academy.business.entity;

import java.io.Serializable;

public class Like implements Serializable {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Like(User user) {
        this.user = user;
    }
}

