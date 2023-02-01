package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ComplaintData implements Serializable {
    public int id;
    public LocalDateTime date;
    public String complaintTxt;
    public CostumerData issuedBy;
    public EmployeeData handledBy;
    public String status;

    public ComplaintData(String comp, int id, CostumerData issuedBy) {
        this.id=id;
        this.date= LocalDateTime.now();
        this.complaintTxt = comp;
        this.issuedBy = issuedBy;
        this.status="open";
        this.handledBy = null;
    }

    public ComplaintData(String comp, int id, LocalDateTime date, CostumerData issuedBy, String status, EmployeeData handledBy) {
        this.id=id;
        this.date= date;
        this.complaintTxt = comp;
        this.issuedBy = issuedBy;
        this.status= status;
        this.handledBy = handledBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getComplaintTxt() {
        return complaintTxt;
    }

    public void setComplaintTxt(String complaintTxt) {
        this.complaintTxt = complaintTxt;
    }

    public CostumerData getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(CostumerData issuedBy) {
        this.issuedBy = issuedBy;
    }

    public EmployeeData getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(EmployeeData handledBy) {
        this.handledBy = handledBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}