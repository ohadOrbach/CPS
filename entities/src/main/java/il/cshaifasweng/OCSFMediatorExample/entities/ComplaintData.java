package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class ComplaintData implements Serializable {
    public int id;
    public LocalDate date;
    public String complaintTxt;
    public CostumerData issuedBy;
    public EmployeeData handledBy;
    public String status;

    public ComplaintData(String comp, int id, CostumerData issuedBy) {
        this.id=id;
        this.date= LocalDate.now();
        this.complaintTxt = comp;
        this.issuedBy = issuedBy;
        this.status="open";
        this.handledBy = null;
    }

    public ComplaintData(String comp, int id, LocalDate date, CostumerData issuedBy, String status, EmployeeData handledBy) {
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
}

/**
 *  public UserData issuedBy;
 *
 *    public ComplaintData(String com, UserData us, int id) {
 *         this.id=id;
 *         complaintDescription = com;
 *         issuedBy = us;
 *     }
 * */