package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingListData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;

import java.util.ArrayList;
import java.util.List;


public class ReceivedParkings {
    private List<ParkingData> parkingDataList;

    public ReceivedParkings(ParkingListData parkingList) {
        this.parkingDataList = new ArrayList<>();
        List<ParkingData> dataList = parkingList.getParkingListData();
        for (ParkingData parking : dataList) {
            parkingDataList.add(parking);
        }
    }

    public List<ParkingData> getParkingDataList() {
        return this.parkingDataList;
    }
}
