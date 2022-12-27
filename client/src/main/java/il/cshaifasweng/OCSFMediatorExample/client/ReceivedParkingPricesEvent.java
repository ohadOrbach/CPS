package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.server.ParkingPrices;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesList;

import java.util.List;

public class ReceivedParkingPricesEvent {
    private PricesList parkingPrices;
    public PricesList getParkingPrices() { return this.parkingPrices; }

    public ReceivedParkingPricesEvent(PricesList parkingPrices) {
        this.parkingPrices = parkingPrices;
    }

    public List<ParkingPricesData> getPricesList() {
        return this.parkingPrices.getPricesList();
    }
}
