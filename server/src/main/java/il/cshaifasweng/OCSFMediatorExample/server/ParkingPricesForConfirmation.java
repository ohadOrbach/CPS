package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ParkingPricesForConfirmation")
public class ParkingPricesForConfirmation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int parkingLotId;
    private double parkingPrice;
    private double orderedParkingPrice;
    private double regularSubscriptionPrice;
    private double regularSubscriptionMultiCarsPrice;
    private double fullySubscriptionPrice;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkinglot", referencedColumnName = "id")
    private ParkingLot parkingLot;



    public ParkingPricesForConfirmation(int parkingLotId, double parkingPrice, double orderedParkingPrice, ParkingLot parkingLot) {
        this.parkingLotId = parkingLotId;
        this.parkingPrice = parkingPrice;
        this.orderedParkingPrice = orderedParkingPrice;
        this.regularSubscriptionPrice = orderedParkingPrice * 60;
        this.regularSubscriptionMultiCarsPrice = orderedParkingPrice * 54;
        this.fullySubscriptionPrice = orderedParkingPrice * 72;
        this.parkingLot = parkingLot;
    }

    public ParkingPricesForConfirmation(ParkingPricesData parkingPricesData, ParkingLot parkingLot) {
        this.parkingLotId = parkingPricesData.getParkingLotId();
        this.parkingPrice = parkingPricesData.getParkingPrice();
        this.orderedParkingPrice = parkingPricesData.getOrderedParkingPrice();
        this.regularSubscriptionPrice = parkingPricesData.getRegularSubscriptionPrice();
        this.regularSubscriptionMultiCarsPrice = parkingPricesData.getRegularSubscriptionMultiCarsPrice();
        this.fullySubscriptionPrice = parkingPricesData.getFullySubscriptionPrice();
        this.parkingLot = parkingLot;
    }

    public ParkingPricesForConfirmation() {

    }

    public int getId() {
        return this.id;
    }

    public double getParkingPrice() {
        return parkingPrice;
    }

    public double getOrderedParkingPrice() {
        return orderedParkingPrice;
    }

    public double getFullySubscriptionPrice() {
        return fullySubscriptionPrice;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public double getRegularSubscriptionPrice() {
        return regularSubscriptionPrice;
    }

    public int getParkingLotId() {
        return parkingLotId;
    }

    public double getRegularSubscriptionMultiCarsPrice() {
        return regularSubscriptionMultiCarsPrice;
    }
}
