package il.cshaifasweng.OCSFMediatorExample.entities;

public class ParkingPrices {
    private int parkingLotId;
    private double parkingPrice;
    private double orderedParkingPrice;
    private double regularSubscriptionPrice;
    private double regularSubscriptionMultiCarsPrice;
    private double fullySubscriptionPrice;

    public ParkingPrices(int parkingLotId, int parkingPrice, int orderedParkingPrice, int regularSubscriptionPrice, int regularSubscriptionMultiCarsPrice, int fullySubscriptionPrice){
        this.parkingLotId = parkingLotId;
        this.parkingPrice = parkingPrice;
        this.orderedParkingPrice = orderedParkingPrice;
        this.regularSubscriptionPrice = regularSubscriptionPrice;
        this.regularSubscriptionMultiCarsPrice = regularSubscriptionMultiCarsPrice;
        this.fullySubscriptionPrice = fullySubscriptionPrice;
    }

    public void setParkingLotId(int parkingLotId){
        this.parkingLotId = parkingLotId;
    }
    public int getParkingLotId(){
        return this.parkingLotId;
    }

    public void setParkingPrice(int parkingPrice){
        this.parkingPrice = parkingPrice;
    }
    public double getParkingPrice(){
        return this.parkingPrice;
    }

    public void setOrderedParkingPrice(int orderedParkingPrice){
        this.orderedParkingPrice = orderedParkingPrice;
    }
    public double getOrderedParkingPrice(){
        return this.orderedParkingPrice;
    }

    public void setRegularSubscriptionPrice(int regularSubscriptionPrice){
        this.regularSubscriptionPrice = regularSubscriptionPrice;
    }
    public double getRegularSubscriptionPrice(){
        return this.regularSubscriptionPrice;
    }

    public void setRegularSubscriptionMultiCarsPrice(int regularSubscriptionMultiCarsPrice){
        this.regularSubscriptionMultiCarsPrice = regularSubscriptionMultiCarsPrice;
    }
    public double getRegularSubscriptionMultiCarsPrice(){
        return this.regularSubscriptionMultiCarsPrice;
    }

    public void setFullySubscriptionPrice(int fullySubscriptionPrice){
        this.fullySubscriptionPrice = fullySubscriptionPrice;
    }
    public double getFullySubscriptionPrice(){
        return this.fullySubscriptionPrice;
    }
}
