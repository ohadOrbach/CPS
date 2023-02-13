package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class ReportData implements Serializable {
    static int reportID = 0;
    public String lotID;
    public int casualNumber;
    public int inAdvanceNumber;
    public int complaintsNumber;
    public int malfunctuinsNumber;
    public LocalDate startDate;
    public LocalDate endDate;
    public int reportNumber;

    public ReportData(String lot_ID, int casual_Number, int inAdvance_Number, int complaints_Number, int malfunctuins_Number, LocalDate start_Date, LocalDate end_Date) {
        this.lotID = lot_ID;
        this.casualNumber = casual_Number;
        this.inAdvanceNumber = inAdvance_Number;
        this.complaintsNumber = complaints_Number;
        this.malfunctuinsNumber = malfunctuins_Number;
        this.startDate = start_Date;
        this.endDate = end_Date;
        this.reportNumber = reportID++;
    }

    public ReportData(int repNum, String lot_ID, int casual_Number, int inAdvance_Number, int complaints_Number, int malfunctuins_Number, LocalDate start_Date, LocalDate end_Date) {
        this.lotID = lot_ID;
        this.casualNumber = casual_Number;
        this.inAdvanceNumber = inAdvance_Number;
        this.complaintsNumber = complaints_Number;
        this.malfunctuinsNumber = malfunctuins_Number;
        this.startDate = start_Date;
        this.endDate = end_Date;
        this.reportNumber = repNum;
    }

    public ReportData(String lot_ID, String casual_Number, String inAdvance_Number, String complaints_Number, String malfunctuins_Number, LocalDate start_Date, LocalDate end_Date) {
        this.lotID = lot_ID;
        this.casualNumber = Integer.parseInt(casual_Number);
        this.inAdvanceNumber = Integer.parseInt(inAdvance_Number);
        this.complaintsNumber = Integer.parseInt(complaints_Number);
        this.malfunctuinsNumber = Integer.parseInt(malfunctuins_Number);
        this.startDate = start_Date;
        this.endDate = end_Date;
        this.reportNumber = reportID++;
    }

    public String getLotId() {
        return lotID;
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

    public int getReportNumber() {
        return reportNumber;
    }

}
