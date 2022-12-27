package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class ParkingLotData implements Serializable {
    public int id;
    public int rows;
    public int size;

    public ParkingLotData(int id, int rows, int rowNum) {
        this.id = id;
        this.rows = rows;
        this.size = rows*rowNum;
    }

    public void setParkingLotId(int parkingLotId){
        this.id = parkingLotId;
    }
    public int getParkingLotId(){
        return this.id;
    }
    public int getSize(){
        return this.size;
    }
    public int getRows(){
        return this.rows;
    }

}
