package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


public class ParkingPricesData implements Serializable {
    private int id;
    private double parkingPrice;
    private double orderedParkingPrice;
    private double regularSubscriptionPrice;
    private double regularSubscriptionMultiCarsPrice;
    private double fullySubscriptionPrice;

    public ParkingPricesData(int parkingLotId, double parkingPrice, double orderedParkingPrice){
        this.id = parkingLotId;
        this.parkingPrice = parkingPrice;
        this.orderedParkingPrice = orderedParkingPrice;
        this.regularSubscriptionPrice = orderedParkingPrice * 60;
        this.regularSubscriptionMultiCarsPrice = orderedParkingPrice * 54;
        this.fullySubscriptionPrice = orderedParkingPrice * 72;
    }

    public ParkingPricesData(List<ParkingPricesData> list){
        this.id = 0;
        this.parkingPrice = 0;
        this.orderedParkingPrice = 0;
        this.regularSubscriptionPrice = 0;
        this.regularSubscriptionMultiCarsPrice = 0;
        this.fullySubscriptionPrice = 0;
    }


    public void setParkingLotId(int parkingLotId){
        this.id = parkingLotId;
    }
    public int getParkingLotId(){
        return this.id;
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
