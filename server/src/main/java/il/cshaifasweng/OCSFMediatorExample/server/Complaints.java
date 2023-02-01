package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Hibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.server.App.SafeStartTransaction;

public class Complaints {
    public List<Complaint> complaints;
    public List<ConnectionToClient> ComplaintClient;
    public Complaints()
    {
        complaints=new ArrayList<>();
        ComplaintClient = new ArrayList<>();
    }

    public String addComplaint(ComplaintData complaintData, Employee emp, ConnectionToClient client){
        App.SafeStartTransaction();
        Complaint com = new Complaint(complaintData, emp);
        App.session.save(com);
        App.session.flush();
        App.SafeCommit();
        complaints.add(com);
        emp.addComplaimt(com);
        ComplaintClient.add(client);
        return com.Respond();
    }
    public ComplaintData GetComplaintData(Complaint c)
    {
        ComplaintData com=new ComplaintData(c.complaintTxt, c.getId(), c.issuedBy.getCostumerData());
        return com;
    }

    public void removeComplaint(int id) {
        for(Complaint c: complaints)
        {
            if(c.getId()==id)
            {
                App.SafeStartTransaction();
                complaints.remove(c);
                App.session.delete(c);
                App.session.flush();
                App.SafeCommit();
            }
        }
    }

    public void pullComplaints() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<Complaint> query = builder.createQuery(Complaint.class);
        query.from(Complaint.class);
        List<Complaint> data = App.session.createQuery(query).getResultList();
        complaints.clear();
        complaints.addAll(data);
    }

    public ConnectionToClient changeStatus(int id) {
        App.SafeStartTransaction();
        Complaint temp = new Complaint();
        for (Complaint comp : complaints) {
            if (comp.getId() == id) {
                temp = comp;
            }
        }
        temp.changeStatus();
        App.session.save(temp);
        App.session.flush();
        App.SafeCommit();
        return ComplaintClient.get(id);
    }

    public ComplaintListData getComplaints() {
        List<ComplaintData> dataList = new ArrayList<>();
        for(Complaint comp: complaints){
            ComplaintData complaintData = comp.getComplaintData();
            dataList.add(complaintData);
        }
        return new ComplaintListData(dataList);
    }
}
