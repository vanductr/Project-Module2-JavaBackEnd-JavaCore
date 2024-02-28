package rikkei.academy.business.entity;

import rikkei.academy.business.design.*;
import rikkei.academy.business.designImpl.CategoryDesignImpl;
import rikkei.academy.business.designImpl.TagDesignImpl;
import rikkei.academy.business.designImpl.UserDesignImpl;
import rikkei.academy.business.util.InputMethods;
import rikkei.academy.business.util.ShopMessage;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable {
    private String postId;
    private String title;
    private String content;
    private User author;
    private LocalDateTime publishDate;
    private LocalDateTime lastModifiedDate;
    private List<Category> categories = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    private List<Like> likes = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();
    private List<Share> shares = new ArrayList<>();
    private boolean approved;
    private ViewPermission permissionViewPost; // Quyền được xem bài viết
    private List<Report> reportList = new ArrayList<>();

    public Post() {
    }

    public Post(String postId, String title, String content, User author, LocalDateTime publishDate, LocalDateTime lastModifiedDate, List<Category> categories, List<Tag> tags) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishDate = publishDate;
        this.lastModifiedDate = lastModifiedDate;
        this.categories = categories;
        this.tags = tags;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public ViewPermission getPermissionViewPost() {
        return permissionViewPost;
    }

    public void setPermissionViewPost(ViewPermission permissionViewPost) {
        this.permissionViewPost = permissionViewPost;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public void inputData(String username, boolean isAdd) {
        System.out.print("Nhập tiêu đề: ");
        this.title = InputMethods.getString();

        System.out.print("Nhập nội dung: ");
        this.content = InputMethods.getString();

        UserDesignImpl userService = new UserDesignImpl();
        this.author = userService.findByUsername(username);

        if (isAdd) {
            this.publishDate = LocalDateTime.now();
        }

        this.lastModifiedDate = this.publishDate;

        addCategoryToPost();

        addTagToPost();

        this.approved = false;

        selectPermissionViewPost();
    }

    public void displayData( boolean isAdmin) {
        System.out.println("ID bài viết: " + this.postId);
        System.out.println("Tiêu đề: " + this.title);
        System.out.println("Nội dung: " + this.content);
        System.out.println("Tác giả: " + this.author.getUsername());
        System.out.println("Ngày đăng bài: " + this.publishDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("Ngày chỉnh sửa gần nhất: " + this.lastModifiedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("Danh mục:");
        for (Category category : categories) {
            System.out.println(" - " + category.getName());
        }
        System.out.println("Tags:");
        for (Tag tag : tags) {
            System.out.println(" - #" + tag.getName());
        }
        if (isAdmin) {
            System.out.println("Trạng thái bài viết: " + (this.approved ? "Đã được Xét duyệt" : "Chưa được xét duyệt"));
        }
        System.out.print("Chế độ hiển thị: ");
        if (this.permissionViewPost == ViewPermission.PUBLIC) {
            System.out.println("Công khai.");
        } else if (this.permissionViewPost == ViewPermission.FRIENDS) {
            System.out.println("Chỉ Bạn bè.");
        } else {
            System.out.println("Riêng tư (Chỉ mình tôi)");
        }
    }

    public void displayDataPost() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Kí tự Unicode cho các đường
        String horizontalLine = "\u2500";
        String verticalLine = "\u2502";
        String topLeftCorner = "\u250C";
        String topRightCorner = "\u2510";
        String bottomLeftCorner = "\u2514";
        String bottomRightCorner = "\u2518";

        // Màu sắc cho các ký tự Unicode
        String cyan = "\u001B[36m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        System.out.println(cyan + topLeftCorner+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + topRightCorner);
        System.out.println(verticalLine + "Post ID: " + postId + " " + verticalLine);
        System.out.println(verticalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ bottomRightCorner+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine + horizontalLine + topRightCorner);
        System.out.println(verticalLine + "                                                                " + "Tiêu Đề: " + title);
        System.out.println(verticalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ verticalLine);
        System.out.println(verticalLine + "Nội Dung: " + content);
        System.out.println(verticalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ verticalLine);
        System.out.println(verticalLine + "Tác giả: " + author.getUsername());
        System.out.println(verticalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ verticalLine);
        System.out.print(verticalLine + "Ngày Đăng: " + publishDate.format(formatter) + "                " + verticalLine);
        System.out.println(verticalLine + "              " + "Ngày cập nhật: " + lastModifiedDate.format(formatter));
        System.out.println(verticalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ verticalLine);

        System.out.print(verticalLine + "Categories: " + verticalLine);
        for (Category category : categories) {
            System.out.print("    @" + category.getName());
        }
        System.out.println();
        System.out.println(verticalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ verticalLine);

        System.out.print(verticalLine + "Tags: " + verticalLine);
        for (Tag tag : tags) {
            System.out.print("    #" + tag.getName());
        }
        System.out.println();
        System.out.println(verticalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ verticalLine);

        System.out.print(verticalLine + "Likes: " + likes.size());
        System.out.print("                        " + "Comments: " + commentList.size());
        System.out.println("                        " + "Shares: " + shares.size());
        System.out.println(bottomLeftCorner + horizontalLine + horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine+ horizontalLine + horizontalLine + horizontalLine + horizontalLine + horizontalLine + bottomRightCorner);
        System.out.print(reset); // Đặt lại màu sắc về mặc định
    }

    private void addCategoryToPost() {
        System.out.println("=== Hãy chọn Danh mục cho bài viết. Dưới đây là Danh sách bài viết:");
        ICategoryDesign categoryService = new CategoryDesignImpl();
        List<Category> categoryList = categoryService.getAll();
        for (Category category : categoryList) {
            category.displayData();
            System.out.println("-------------------------");
        }
        while (true) {
            System.out.print("Nhập 1 để tiếp tục chọn Danh mục, 0 để thôi chọn: ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                Category category;
                while (true) {
                    System.out.print("Nhập Id của Danh mục để chọn Danh mục đó: ");
                    String categoryId = InputMethods.getString();
                    category = categoryService.findById(categoryId);
                    if (category == null) {
                        System.err.println("Id nhập vào không đúng, hãy kiểm tra lại.");
                    } else {
                        break;
                    }
                }
                this.categories.add(category);
                System.out.println("Bạn vừa thêm 1 Danh mục cho Bài viết của mình.");
            } else if (choice == 0) {
                break;
            } else {
                System.err.println("Lựa chọn không hợp lệ, hãy kiểm tra lại.");
            }
        }
    }

    private void addTagToPost() {
        System.out.println("=== Tiếp theo, hãy chọn Các Tag cho Bài Post của bạn. Danh sách các Tag:");
        ITagDesign tagService = new TagDesignImpl();
        List<Tag> tagList = tagService.getAll();
        for (Tag tag : tagList) {
            tag.displayData();
            System.out.println("----------------------------");
        }
        while (true) {
            System.out.print("Nhập 1 để tiếp tục chọn Tag; 0 để Thôi chọn: ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                Tag tag;
                while (true) {
                    System.out.print("Nhập Tag Id để chọn Tag: ");
                    String tagId = InputMethods.getString();
                    tag = tagService.findById(tagId);
                    if (tag == null) {
                        System.err.println("Id nhâp vào không đúng. Hãy kiểm tra lại.");
                    } else {
                        break;
                    }
                }
                this.tags.add(tag);
                System.out.println("Bạn vừa thêm 1 Tag cho Bài viết của mình.");
            }else if (choice == 0) {
                break;
            } else {
                System.err.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    public void selectPermissionViewPost() {
        System.out.println("===== Ai có thể xem bài viết của bạn:");
        while (true) {
            System.out.println("=== Cài đặt quyền được xem bài viết:");
            System.out.println("1. Công khai");
            System.out.println("2. Bạn bè của tôi");
            System.out.println("3. Chỉ mình tôi");
            System.out.print("Nhập lựa chọn: ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                this.permissionViewPost = ViewPermission.PUBLIC;
                System.out.println("===== Bài viết ở chế độ: Công khai");
                return;
            } else if (choice == 2) {
                this.permissionViewPost = ViewPermission.FRIENDS;
                System.out.println("===== Bài viết ở chế độ: Chỉ Bạn bè mới xem được");
                return;
            } else if (choice == 3) {
                this.permissionViewPost = ViewPermission.PRIVATE;
                System.out.println("===== Bài viết ở chế độ: Chỉ mình tôi.");
                return;
            } else {
                System.err.println(ShopMessage.INVALID_SELECTION_ERROR);
            }
        }
    }
}

