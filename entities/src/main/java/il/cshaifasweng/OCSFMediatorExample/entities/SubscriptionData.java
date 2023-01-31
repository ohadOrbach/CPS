package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class SubscriptionData implements Serializable
{
    private String type;

    private String carNum;

    private LocalDate endingDate;

    public SubscriptionData(String type, String carNum, LocalDate endingDate) {
        this.type = type;
        this.carNum = carNum;
        this.endingDate = endingDate;
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
}