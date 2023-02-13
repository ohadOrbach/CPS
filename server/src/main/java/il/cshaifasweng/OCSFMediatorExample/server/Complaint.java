package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintData;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaint")
public class Complaint {

    @Column(length = 1000)
    public String complaintTxt;
    public LocalDateTime date;
    public String status;
    @ManyToOne(fetch = FetchType.LAZY)
    public Costumer issuedBy;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Employee handledBy;

    public Complaint() {
    }

    public Complaint(ComplaintData com, Employee emp) {
        this.date = com.date;
        this.complaintTxt = com.complaintTxt;
        this.issuedBy = App.costumers.getCostumer((com.issuedBy.getId()));
        this.handledBy = emp;
        this.status = com.status;
    }

    public ComplaintData getComplaintData() {
        ComplaintData com = new ComplaintData(complaintTxt, id, date, issuedBy.getCostumerData(), status, handledBy.getEmployeeData());
        return com;
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

    public String getcomplaintTxt() {
        return complaintTxt;
    }

    public String Respond() {
        return "We are very sorry to hear your complaint\nWe will get back to you in 24 hours or less \nThank you for your understanding";
    }

    public void setcomplaintTxt(String complaintTxt) {
        this.complaintTxt = complaintTxt;
    }

    public String getStatus() {
        return status;
    }

    public void changeStatus() {
        this.status = "closed";
    }

    public Costumer getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Costumer issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Employee getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(Employee handledBy) {
        this.handledBy = handledBy;
    }

    public int checkReminder() {
        if (this.status.equals("open")) {
            return 1;
        } else {
            return 0;
        }
    }

}
