package il.cshaifasweng.OCSFMediatorExample.entities;

public class ParkingLotId {
    private int parkingLotId;
    private int size;

    public ParkingLotId(int parkingLotId, int size){
        this.parkingLotId = parkingLotId;
        this.size = size;
    }

    public void setParkingLotId(int parkingLotId){
        this.parkingLotId = parkingLotId;
    }
    public int getParkingLotId(){
        return this.parkingLotId;
    }

    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }

}
