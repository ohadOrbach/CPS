package il.cshaifasweng.OCSFMediatorExample.entities;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class ParkingListData implements Serializable{
    public List<ParkingData> parkingDataList;
    public ParkingListData(){ this.parkingDataList = new ArrayList<>(); }

    public ParkingListData(List<ParkingData> dataList){
        this.parkingDataList = new ArrayList<>();
        for(ParkingData parkingData: dataList){
            parkingDataList.add(parkingData);
        }

    }

    public List<ParkingData> getParkingListData() {
        return this.parkingDataList;
    }

}
