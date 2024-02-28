package rikkei.academy.business.entity;

import rikkei.academy.business.util.InputMethods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private String groupId;
    private String groupName;
    private String description;
    private User leader;
    private List<User> members = new ArrayList<>();
    private List<User> requests = new ArrayList<>();
    private List<Report> reports = new ArrayList<>();
    private Boolean status;

    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public User getLeader() {
        return leader;
    }
    public void setLeader(User leader) {
        this.leader = leader;
    }
    public List<User> getMember() {
        return members;
    }
    public void setMember(List<User> members) {
        this.members = members;
    }
    public List<User> getRequest() {
        return requests;
    }
    public void setRequest(List<User> requests) {
        this.requests = requests;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void inputDataGroup() {
        System.out.print("Nhập tên Group: ");
        this.groupName = InputMethods.getString();
        System.out.print("Nhập mô tả Group: ");
        this.description = InputMethods.getString();

        this.status = true;
    }
    public void displayDataGroup() {
        System.out.println("Group Id: " + this.groupId);
        System.out.println("Tên Group: " + this.groupName);
        System.out.println("Mô tả: " + this.description);
        System.out.println("Trưởng nhóm: " + this.leader.getFullName());
        System.out.println("Thành viên trong nhóm:");
        for (User user : this.members) {
            System.out.print(user.getFullName() + " | ");
        }
        System.out.println();
    }
}
