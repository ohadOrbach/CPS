package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ReceivedComplaintsEvent {
    private List<ComplaintData> complaintsDataList;
    public List<ComplaintData> getComplaints() { return this.complaintsDataList; }


    public ReceivedComplaintsEvent(ComplaintListData complaintsList) {
        this.complaintsDataList = new ArrayList<>();
        List<ComplaintData> dataList = complaintsList.getComplaints();
        for(ComplaintData complaints: dataList){
            complaintsDataList.add(complaints);
//            System.out.println("adding on reciveing\n");
        }
    }

}
