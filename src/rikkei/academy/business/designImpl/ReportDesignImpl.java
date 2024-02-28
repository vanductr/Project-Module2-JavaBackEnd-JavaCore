package rikkei.academy.business.designImpl;

import rikkei.academy.business.design.IReportDesign;
import rikkei.academy.business.entity.Report;
import rikkei.academy.business.util.IOFile;
import rikkei.academy.business.util.ShopConstants;

import java.util.List;

public class ReportDesignImpl implements IReportDesign {
    List<Report> reportList;

    public ReportDesignImpl() {
        reportList = IOFile.readFromFile(ShopConstants.REPORT_PATH);
    }

    @Override
    public List<Report> getAll() {
        return reportList;
    }

    @Override
    public void save(Report report) {
        if (findById(report.getReportId()) == null) {
            reportList.add(report);
        } else {
            reportList.set(reportList.indexOf(findById(report.getReportId())), report);
        }
        IOFile.writeToFile(ShopConstants.REPORT_PATH, reportList);
    }

    @Override
    public Report findById(String string) {
        for (Report report : reportList) {
            if (report.getReportId().equals(string)) {
                return report;
            }
        }
        return null;
    }

    @Override
    public void delete(Report report) {
        reportList.remove(report);
        IOFile.writeToFile(ShopConstants.REPORT_PATH, reportList);
    }

    @Override
    public String getNewId() {
        int idMax = 0;
        for (Report report : reportList) {
            int reportId = Integer.parseInt(report.getReportId().replace("R", "0"));
            if (idMax < reportId) {
                idMax = reportId;
            }
        }
        idMax += 1;
        return "R" + String.format("%03d", idMax);
    }
}
