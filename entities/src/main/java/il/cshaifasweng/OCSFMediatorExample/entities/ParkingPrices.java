package il.cshaifasweng.OCSFMediatorExample.entities;

public class ParkingPrices {
    private int parkingLotId;
    private double parkingPrice;
    private double orderedParkingPrice;
    private double regularSubscriptionPrice;
    private double regularSubscriptionMultiCarsPrice;
    private double fullySubscriptionPrice;

    public ParkingPrices(int parkingLotId, int parkingPrice, int orderedParkingPrice){
        this.parkingLotId = parkingLotId;
        this.parkingPrice = parkingPrice;
        this.orderedParkingPrice = orderedParkingPrice;
        this.regularSubscriptionPrice = orderedParkingPrice * 60;
        this.regularSubscriptionMultiCarsPrice = orderedParkingPrice * 54;
        this.fullySubscriptionPrice = orderedParkingPrice * 72;
    }

    public ParkingPrices(){
        this.parkingLotId = 0;
        this.parkingPrice = 0;
        this.orderedParkingPrice = 0;
        this.regularSubscriptionPrice = 0;
        this.regularSubscriptionMultiCarsPrice = 0;
        this.fullySubscriptionPrice = 0;
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
        this.regularSubscriptionPrice = orderedParkingPrice * 60;
        this.regularSubscriptionMultiCarsPrice = orderedParkingPrice * 54;
        this.fullySubscriptionPrice = orderedParkingPrice * 72;
    }
    public double getOrderedParkingPrice(){
        return this.orderedParkingPrice;
    }

    public double getRegularSubscriptionPrice(){
        return this.regularSubscriptionPrice;
    }

    public double getRegularSubscriptionMultiCarsPrice() { return this.regularSubscriptionMultiCarsPrice;}

    public double getFullySubscriptionPrice(){
        return this.fullySubscriptionPrice;
    }
}
