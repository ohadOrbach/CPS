package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class StastisticalInformationData implements Serializable {
    private int id;
    private int parkingLotId;
    private String name;
    private LocalDate date;
    private double actualOrders;
    private double canceledOrders;
    private double parkingLateNum;


    public StastisticalInformationData(int id, int parkingLotId, String name, LocalDate date, double actualOrders, double canceledOrders, double parkingLateNum) {
        this.id = id;
        this.parkingLotId = parkingLotId;
        this.name = name;
        this.date = date;
        this.actualOrders = actualOrders;
        this.canceledOrders = canceledOrders;
        this.parkingLateNum = parkingLateNum;
    }

    public StastisticalInformationData() {
    }


    public double getActualOrders() {
        return actualOrders;
    }

    public void setActualOrders(double actualOrders) {
        this.actualOrders = actualOrders;
    }

    public double getCanceledOrders() {
        return canceledOrders;
    }

    public void setCanceledOrders(double canceledOrders) {
        this.canceledOrders = canceledOrders;
    }

    public double getParkingLateNum() {
        return parkingLateNum;
    }

    public void setParkingLateNum(double parkingLateNum) {
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
