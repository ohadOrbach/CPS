package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ParkingLot;
import il.cshaifasweng.OCSFMediatorExample.server.Subscription;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "regular_subscriptions")
public class RegularSubscription extends Subscription {

    @ManyToOne
    @JoinColumn(name = "parkinglot_id")
    private ParkingLot parkingLot;
    private String expectedLeavingTime;

    public RegularSubscription(int subscriptionId, Costumer costumer, String licencePlate, LocalDate startingDate, ParkingLot parkingLot, String expectedLeavingTime) {
        super(subscriptionId, costumer, licencePlate, startingDate);
        this.parkingLot = parkingLot;
        this.expectedLeavingTime = expectedLeavingTime;
    }

    public RegularSubscription() {

    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getExpectedLeavingTime() {
        return expectedLeavingTime;
    }

    public void setExpectedLeavingTime(String expectedLeavingTime) {
        this.expectedLeavingTime = expectedLeavingTime;
    }
}


