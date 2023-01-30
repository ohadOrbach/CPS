package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class StastisticalInformationData implements Serializable {
    private int id;
    private int parkingLotId;
    private String name;
    private LocalDate date;
    private int actualOrders;
    private int canceledOrders;
    private int parkingLateNum;


    public StastisticalInformationData(int id, int parkingLotId ,String name ,LocalDate date, int actualOrders, int canceledOrders, int parkingLateNum) {
        this.id = id;
        this.parkingLotId = parkingLotId;
        this.name = name;
        this.date = date;
        this.actualOrders = actualOrders;
        this.canceledOrders = canceledOrders;
        this.parkingLateNum = parkingLateNum;
    }

    public StastisticalInformationData(){}


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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(int parkingLotId) {
        this.parkingLotId = parkingLotId;
    }


}