package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;

public class ReportListData implements Serializable {
    public List<ReportData> reports;

    public ReportListData(List<ReportData> reports) {
        this.reports = reports;
    }

    public List<ReportData> getReports() {
        return reports;
    }

    public void setReports(List<ReportData> reports) {
        this.reports = reports;
    }
}

