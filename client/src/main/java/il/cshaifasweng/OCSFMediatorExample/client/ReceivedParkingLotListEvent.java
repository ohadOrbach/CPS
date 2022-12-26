package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotList;

import java.security.PublicKey;

public class ReceivedParkingLotListEvent {
    private ParkingLotList parkingLotList;
    public ParkingLotList getParkingLotList() { return parkingLotList; }

    public ReceivedParkingLotListEvent(ParkingLotList parkingLotList) {
        this.parkingLotList = parkingLotList;
    }
}
