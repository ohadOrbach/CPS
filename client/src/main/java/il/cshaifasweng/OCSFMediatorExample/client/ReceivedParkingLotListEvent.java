package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotList;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;


public class ReceivedParkingLotListEvent {
    private ParkingLotListData parkingLotList;
    public ParkingLotListData getParkingLotList() { return parkingLotList; }

    public ReceivedParkingLotListEvent(ParkingLotListData parkingLotList) {
        this.parkingLotList = parkingLotList;
    }
}
