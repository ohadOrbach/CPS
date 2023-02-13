package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;

import java.util.ArrayList;
import java.util.List;


public class ReceivedParkingLotListEvent {
    private List<ParkingLotData> parkingLotDataList;

    public ReceivedParkingLotListEvent(ParkingLotListData parkingLotList) {
        this.parkingLotDataList = new ArrayList<>();
        List<ParkingLotData> dataList = parkingLotList.getParkingLotListData();
        for (ParkingLotData parking : dataList) {
            parkingLotDataList.add(parking);
        }
    }

    public List<ParkingLotData> getParkingLotDataList() {
        return this.parkingLotDataList;
    }
}
