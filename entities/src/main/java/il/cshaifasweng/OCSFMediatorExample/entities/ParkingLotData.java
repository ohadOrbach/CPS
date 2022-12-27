package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class ParkingLotData implements Serializable {
    public int id;
    public int rows;
    public int size;

    public ParkingLotData(int id, int rows, int size) {
        this.id = id;
        this.rows = rows;
        this.size = size;
    }

    public void setParkingLotId(int parkingLotId){
        this.id = parkingLotId;
    }
    public int getParkingLotId(){
        return this.id;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }

}
