package rikkei.academy.business.entity;

import rikkei.academy.business.util.InputMethods;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable {
    private String commentId;
    private User author;
    private String content;
    private LocalDateTime commentDate;

    public Comment() {
    }

    public Comment(String commentId, User author, String content, LocalDateTime commentDate) {
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.commentDate = commentDate;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public void inputData() {
        System.out.print("Nhập nội dung bình luận: ");
        this.content = InputMethods.getString();
        this.commentDate = LocalDateTime.now();
    }

    public void displayData() {
        System.out.println("ID bình luận: " + commentId);
        System.out.println("Tác giả: " + author.getUsername());
        System.out.println("Nội dung: " + content);
        System.out.println("Ngày bình luận: " + commentDate);
    }
}

