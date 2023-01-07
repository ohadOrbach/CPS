package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class ParkingData implements Serializable {
    private int id;
    private int status; //0-free, 1-taken, 2-error
    private int place;
    public ParkingData(int status, int place) {
        this.status = status;
        this.place = place;
    }

    public int getParkingId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
