package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotListData implements Serializable {
    public List<ParkingLotData> pricesList;

    public ParkingLotListData() {
        this.pricesList = new ArrayList<>();
    }

    public ParkingLotListData(List<ParkingLotData> dataList) {
        this.pricesList = new ArrayList<>();
        for (ParkingLotData parkingLotData : dataList) {
            pricesList.add(parkingLotData);
        }

    }

    public List<ParkingLotData> getParkingLotListData() {
        return this.pricesList;
    }

}

