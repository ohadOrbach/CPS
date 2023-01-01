package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;

public class ComplaintListData implements Serializable {
    public List<ComplaintData> complaints;

    public ComplaintListData(List<ComplaintData> complaints) {
        this.complaints = complaints;
    }

    public List<ComplaintData> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<ComplaintData> complaints) {
        this.complaints = complaints;
    }
}
