package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;

import java.util.ArrayList;
import java.util.List;

public class ReceivedReportsEvent {
    private List<ReportData> reportDataList;
    public List<ReportData> getReports() { return this.reportDataList; }


    public ReceivedReportsEvent(ReportListData reportList) {
        this.reportDataList = new ArrayList<>();
        List<ReportData> dataList = reportList.getReports();
        for(ReportData reports: dataList){
            reportDataList.add(reports);
//            System.out.println("adding on reciveing\n");
        }
    }
    public List<ReportData> getReportDataList() {
        return this.reportDataList;
    }
}
