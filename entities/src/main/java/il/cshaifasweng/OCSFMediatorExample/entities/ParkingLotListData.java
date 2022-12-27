package il.cshaifasweng.OCSFMediatorExample.entities;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ParkingLotListData implements Serializable{
    public List<ParkingLotData> parkingLotList;
    public ParkingLotListData(List<ParkingLotData> list)
    {
        parkingLotList = new ArrayList<ParkingLotData>();
    }
    public void setParkingLotsList(List<ParkingLotData> complaints) {
        this.parkingLotList = parkingLotList;
    }
    public List<ParkingLotData> getParkingLots() {
        return parkingLotList;
    }
}
