package il.cshaifasweng.OCSFMediatorExample.server;


import il.cshaifasweng.OCSFMediatorExample.entities.ReportData;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reports")

public class Report {

    static int reportID = 0;
    public int casualNumber;
    public int inAdvanceNumber;
    public int complaintsNumber;
    public int malfunctuinsNumber;
    public LocalDate startDate;
    public LocalDate endDate;
    public int reportNumber;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;
    private String LotId;

    public Report(String lot_ID, int casual_Number, int inAdvance_Number, int complaints_Number, int malfunctuins_Number, LocalDate start_Date, LocalDate end_Date) {
        this.LotId = lot_ID;
        this.casualNumber = casual_Number;
        this.inAdvanceNumber = inAdvance_Number;
        this.complaintsNumber = complaints_Number;
        this.malfunctuinsNumber = malfunctuins_Number;
        this.startDate = start_Date;
        this.endDate = end_Date;
        this.reportNumber = reportID++;
    }

    public Report(ReportData rep) {
        LotId = rep.lotID;
        casualNumber = rep.casualNumber;
        inAdvanceNumber = rep.inAdvanceNumber;
        complaintsNumber = rep.complaintsNumber;
        malfunctuinsNumber = rep.malfunctuinsNumber;
        startDate = rep.startDate;
        endDate = rep.endDate;
        reportNumber = rep.reportNumber;
    }

    public String getLotId() {
        return LotId;
    }

    public int getCasualNumber() {
        return casualNumber;
    }

    public int getInAdvanceNumber() {
        return inAdvanceNumber;
    }

    public int getComplaintsNumber() {
        return complaintsNumber;
    }

    public int getMalfunctuinsNumber() {
        return malfunctuinsNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getReportId() {
        return reportId;
    }


    public ReportData getReportData() {
        ReportData reportData = new ReportData(reportNumber, LotId, casualNumber, inAdvanceNumber, complaintsNumber, malfunctuinsNumber, startDate, endDate);
        return reportData;
    }

}
