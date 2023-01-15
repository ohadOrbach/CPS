package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class ComplaintData implements Serializable {
    public int id;
    public String complaintTxt;
    public CostumerData issuedBy;

    public ComplaintData(String comp, int id, CostumerData issuedBy) {
        this.id=id;
        this.complaintTxt = comp;
        this.issuedBy = issuedBy;
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