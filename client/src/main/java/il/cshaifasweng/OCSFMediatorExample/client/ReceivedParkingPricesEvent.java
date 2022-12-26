package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPrices;

public class ReceivedParkingPricesEvent {
    private ParkingPrices parkingPrices;
    public  ParkingPrices getParkingPrices() { return this.parkingPrices; }

    public ReceivedParkingPricesEvent(ParkingPrices parkingPrices) {
        this.parkingPrices = parkingPrices;
    }
}
