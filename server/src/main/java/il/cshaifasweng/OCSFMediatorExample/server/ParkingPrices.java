package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ParkingPrices")
public class ParkingPrices implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double parkingPrice;
    private double orderedParkingPrice;
    private double regularSubscriptionPrice;
    private double regularSubscriptionMultiCarsPrice;
    private double fullySubscriptionPrice;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkinglot", referencedColumnName = "id")
    private ParkingLot parkingLot;

    public ParkingPrices(int parkingLotId, double parkingPrice, double orderedParkingPrice, ParkingLot parkingLot){
        this.id = parkingLotId;
        this.parkingPrice = parkingPrice;
        this.orderedParkingPrice = orderedParkingPrice;
        this.regularSubscriptionPrice = orderedParkingPrice * 60;
        this.regularSubscriptionMultiCarsPrice = orderedParkingPrice * 54;
        this.fullySubscriptionPrice = orderedParkingPrice * 72;
        this.parkingLot = parkingLot;
    }

    public ParkingPrices(List<ParkingPrices> list){
        this.id = 0;
        this.parkingPrice = 0;
        this.orderedParkingPrice = 0;
        this.regularSubscriptionPrice = 0;
        this.regularSubscriptionMultiCarsPrice = 0;
        this.fullySubscriptionPrice = 0;
    }

    public ParkingPrices() {

    }

    public void setParkingLotId(int parkingLotId){
        this.id = parkingLotId;
    }
    public int getParkingLotId(){
        return this.id;
    }

    public void setParkingPrice(double parkingPrice){
        this.parkingPrice = parkingPrice;
    }
    public double getParkingPrice(){
        return this.parkingPrice;
    }

    public void setOrderedParkingPrice(double orderedParkingPrice){
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

    public void setRegularSub(double newPrice) { this.regularSubscriptionPrice = newPrice; }

    public void setRegularSubMulti(double newPrice) { this.regularSubscriptionMultiCarsPrice = newPrice; }

    public void setFullSubPrice(double newPrice) { this.fullySubscriptionPrice = newPrice; }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
