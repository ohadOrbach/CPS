package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;


public class ParkingPricesData implements Serializable {
    private int id;
    private int parkingLotId;
    private double parkingPrice;
    private double orderedParkingPrice;
    private double regularSubscriptionPrice;
    private double regularSubscriptionMultiCarsPrice;
    private double fullySubscriptionPrice;

    public ParkingPricesData(int parkingLotId, double parkingPrice, double orderedParkingPrice) {
        this.parkingLotId = parkingLotId;
        this.parkingPrice = parkingPrice;
        this.orderedParkingPrice = orderedParkingPrice;
        this.regularSubscriptionPrice = orderedParkingPrice * 60;
        this.regularSubscriptionMultiCarsPrice = orderedParkingPrice * 54;
        this.fullySubscriptionPrice = orderedParkingPrice * 72;
    }

    public ParkingPricesData(List<ParkingPricesData> list) {
        this.parkingLotId = 0;
        this.parkingPrice = 0;
        this.orderedParkingPrice = 0;
        this.regularSubscriptionPrice = 0;
        this.regularSubscriptionMultiCarsPrice = 0;
        this.fullySubscriptionPrice = 0;
    }

    public int getId(){return id;}
    public int getParkingLotId() {
        return this.parkingLotId;
    }

    public void setParkingLotId(int parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public double getParkingPrice() {
        return this.parkingPrice;
    }

    public void setParkingPrice(double parkingPrice) {
        this.parkingPrice = parkingPrice;
    }

    public double getOrderedParkingPrice() {
        return this.orderedParkingPrice;
    }

    public void setOrderedParkingPrice(double orderedParkingPrice) {
        this.orderedParkingPrice = orderedParkingPrice;
        this.regularSubscriptionPrice = orderedParkingPrice * 60;
        this.regularSubscriptionMultiCarsPrice = orderedParkingPrice * 54;
        this.fullySubscriptionPrice = orderedParkingPrice * 72;
    }

    public double getRegularSubscriptionPrice() {
        return this.regularSubscriptionPrice;
    }

    public double getRegularSubscriptionMultiCarsPrice() {
        return this.regularSubscriptionMultiCarsPrice;
    }

    public double getFullySubscriptionPrice() {
        return this.fullySubscriptionPrice;
    }
}
