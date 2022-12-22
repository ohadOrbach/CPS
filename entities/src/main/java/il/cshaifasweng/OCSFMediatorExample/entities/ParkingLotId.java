package il.cshaifasweng.OCSFMediatorExample.entities;

public class ParkingLotId {
    private int parkingLotId;
    private String name;
    private int size;
    private ParkingPrices prices;

    public ParkingLotId(int parkingLotId, String name, int size, ParkingPrices prices){
        this.parkingLotId = parkingLotId;
        this.name=name;
        this.size = size;
        this.prices = new ParkingPrices();
    }

    public void setParkingLotId(int parkingLotId){
        this.parkingLotId = parkingLotId;
    }
    public int getParkingLotId(){
        return this.parkingLotId;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }
    public void setPrices(ParkingPrices prices){
        this.prices = prices;
    }
    public ParkingPrices getPrices(){
        return this.prices;
    }

}
