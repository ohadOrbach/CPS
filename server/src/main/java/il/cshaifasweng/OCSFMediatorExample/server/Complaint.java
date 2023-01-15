package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintData;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length=1000)
    public String complaintTxt;
    public LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    public Costumer issuedBy;
    @ManyToOne
    private Employee handledBy;

    public Complaint() {}
    public Complaint(ComplaintData com, Employee emp)
    {
        this.date= LocalDate.now();
        this.complaintTxt=com.complaintTxt;
        this.issuedBy = App.costumers.getCostumer((com.issuedBy.getId()));
        this.handledBy = emp;
    }
    public ComplaintData getComplaintData()
    {
        ComplaintData com = new ComplaintData(complaintTxt, id, issuedBy.getCostumerData());
        return com;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getcomplaintTxt() {
        return complaintTxt;
    }
    public String Respond()
    {
        return "We are very sorry to hear your complaint\nWe will get back to you in 24 hours or less \nThank you for your understanding";
    }
    public void setcomplaintTxt(String complaintTxt) {
        this.complaintTxt = complaintTxt;
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

    public void checkReminder() {
        LocalDate now = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(date, now);
        if (daysBetween >= 1) {
            System.out.println("Reminder: This complaint has not been resolved yet, please attend to it. ");
        }
    }

}
