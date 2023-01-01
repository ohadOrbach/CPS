package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintData;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length=1000)
    public String complaintTxt;
    public LocalDate date;
    
/**
    @ManyToOne(fetch = FetchType.LAZY)
    public User issuedBy;
*/
    public Complaint() {}
    public Complaint(ComplaintData com)
    {
        date= LocalDate.now();
        complaintTxt=com.complaintTxt;
    }
    public ComplaintData getComplaintData()
    {
        ComplaintData com = new ComplaintData(complaintTxt, id);
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
        return "We are very sorry to hear your complaint, we are going to check the incident and understand what we did wrong\nThank you for your understanding";
    }
    public void setcomplaintTxt(String complaintTxt) {
        this.complaintTxt = complaintTxt;
    }

    /**
    public User getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(User issuedBy) {
        this.issuedBy = issuedBy;
    }
     */
}
