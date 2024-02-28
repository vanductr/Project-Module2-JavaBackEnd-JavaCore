package rikkei.academy.business.entity;

import java.io.Serializable;

public class Share implements Serializable {
    private User user;

    public Share() {
    }

    public Share(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void displayData() {
        System.out.println("Người Share: " + this.user.getFullName());
    }

}

