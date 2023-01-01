package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class ComplaintData implements Serializable {
    public int id;
    public String complaintTxt;

    public ComplaintData(String comp, int id) {
        this.id=id;
        complaintTxt = comp;
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