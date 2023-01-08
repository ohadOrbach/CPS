package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class StastisticalInformationData implements Serializable {
    private int id;
    private LocalDateTime date;
    private int actualOrders;
    private int canceledOrders;
    private int parkingLateNum;


    public StastisticalInformationData(int id,LocalDateTime date, int actualOrders, int canceledOrders, int parkingLateNum) {
        this.id = id;
        this.date = date;
        this.actualOrders = actualOrders;
        this.canceledOrders = canceledOrders;
        this.parkingLateNum = parkingLateNum;
    }




    public int getActualOrders() {
        return actualOrders;
    }

    public void setActualOrders(int actualOrders) {
        this.actualOrders = actualOrders;
    }

    public int getCanceledOrders() {
        return canceledOrders;
    }

    public void setCanceledOrders(int canceledOrders) {
        this.canceledOrders = canceledOrders;
    }

    public int getParkingLateNum() {
        return parkingLateNum;
    }

    public void setParkingLateNum(int parkingLateNum) {
        this.parkingLateNum = parkingLateNum;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
