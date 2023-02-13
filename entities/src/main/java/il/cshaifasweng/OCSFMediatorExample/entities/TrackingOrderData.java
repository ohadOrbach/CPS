package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class TrackingOrderData implements Serializable {
    int id;
    int carNum;

    public TrackingOrderData(int id, int carNum) {
        this.id = id;
        this.carNum = carNum;
    }

    public int getId() {
        return this.id;
    }

    public int getCarNum() {
        return this.carNum;
    }


}
