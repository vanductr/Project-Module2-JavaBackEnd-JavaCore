package rikkei.academy.business.entity;

import rikkei.academy.business.util.InputMethods;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Report implements Serializable {
    private String reportId;
    private User reporterUser;
    private String reason;
    private String reportContent;
    private LocalDateTime reportedDate; // Ngày Report
    private Boolean status; // Đã được xử lý hay chưa
    private LocalDateTime resolvedDate; // Ngày xử lý

    public Report() {
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public User getReporterUser() {
        return reporterUser;
    }

    public void setReporterUser(User reporterUser) {
        this.reporterUser = reporterUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public LocalDateTime getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(LocalDateTime reportedDate) {
        this.reportedDate = reportedDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public void inputDataReport() {
        System.out.print("Nhập lý do: ");
        this.reason = InputMethods.getString();
        System.out.print("Nhập nội dung cụ thể: ");
        this.reportContent = InputMethods.getString();

        this.reportedDate = LocalDateTime.now();

        this.status = false;
    }

    public void displayData() {
        System.out.println("ID Báo cáo: " + this.reportId);
        System.out.println("Người Báo cáo: " + this.reporterUser.getUsername());
        System.out.println("Lý do: " + this.reason);
        System.out.println("Nội dung báo cáo: " + this.reportContent);
        System.out.println("Ngày Báo cáo: " + this.reportedDate);
        System.out.println("Trạng thái: " + (this.status ? "Đã xử lý" : "Chưa xử lý"));
        if (this.status) {
            System.out.println("Ngày xử lý: " + this.resolvedDate);
        }
    }

}
