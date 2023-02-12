package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ReportData;
import il.cshaifasweng.OCSFMediatorExample.entities.ReportListData;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class Reports {

    public List<Report> reportsList;

    public Reports() {
        reportsList = new ArrayList<>();
    }

    public String addReport(ReportData reportData) {
        App.SafeStartTransaction();
        Report report = new Report(reportData.getLotId(), reportData.getCasualNumber(), reportData.getInAdvanceNumber(),
                reportData.getComplaintsNumber(), reportData.getMalfunctuinsNumber(), reportData.getStartDate(), reportData.getEndDate());
        App.session.save(report);
        App.session.flush();
        App.SafeCommit();
        reportsList.add(report);
        return "Your report has been successfully submitted.";
    }

    public ReportData getReportData(Report report) {
        return report.getReportData();
    }

    public void pullReports() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<Report> query = builder.createQuery(Report.class);
        query.from(Report.class);
        List<Report> data = App.session.createQuery(query).getResultList();
        reportsList.clear();
        reportsList.addAll(data);
    }

    /*
    private ReportListData findReportData(int id, LocalDate start) {
        List<ReportData> list = new ArrayList<>();
        for(Report report: reportsList){
            if(report.getLotId().equals(id) && report.getStartDate().equals(start))
                list.add(report.getReportData());
        }
        return new ReportListData(list);
    }*/

    public ReportListData getReportsList() {
        List<ReportData> reportList = new ArrayList<>();
        System.out.println("in this");
        for (Report report : reportsList) {
            ReportData reportData = report.getReportData();
            System.out.println("add report " + reportData.getLotId());
            reportList.add(reportData);
        }
        return new ReportListData(reportList);
    }
}
