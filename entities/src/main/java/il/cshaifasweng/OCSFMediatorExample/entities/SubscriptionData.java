package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class SubscriptionData implements Serializable
{

    private String costumerId;

    private String type;

    private String carNum;

    private LocalDate endingDate;

    public SubscriptionData(String type, String carNum, LocalDate endingDate,String costumerId) {
        this.type = type;
        this.carNum = carNum;
        this.endingDate = endingDate;
        this.costumerId = costumerId;
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

    public String getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(String costumerId) {
        this.costumerId = costumerId;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }
}