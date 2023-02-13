package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComplaintListData implements Serializable {
    public List<ComplaintData> complaints;

    public ComplaintListData(List<ComplaintData> datalist) {

        this.complaints = new ArrayList<>();
        for (ComplaintData compData : datalist) {
            complaints.add(compData);
        }
    }

    public void remove(ComplaintData compData) {
        complaints.remove(complaints.remove(compData));
    }

    public List<ComplaintData> getComplaints() {
        return this.complaints;
    }

    public void setComplaints(List<ComplaintData> complaints) {
        this.complaints = complaints;
    }
}
