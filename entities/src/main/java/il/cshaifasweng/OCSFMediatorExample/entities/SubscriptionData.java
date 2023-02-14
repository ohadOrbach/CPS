package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class SubscriptionData implements Serializable {

    private String costumerId;

    private String type;

    private String carNum;

    private LocalDate endingDate;

    private String parkingLot;

    private String subscriptionId;

    public SubscriptionData(String type, String carNum, LocalDate endingDate, String costumerId,String parkingLot) {
        this.type = type;
        this.carNum = carNum;
        this.endingDate = endingDate;
        this.costumerId = costumerId;
        this.parkingLot=parkingLot;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public String getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(String costumerId) {
        this.costumerId = costumerId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}