package il.cshaifasweng.OCSFMediatorExample.entities;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ParkingLotList implements Serializable{
    public List<ParkingLotId> parkinglotlist;
    public ParkingLotList()
    {
        parkinglotlist = new ArrayList<ParkingLotId>();
    }
}
