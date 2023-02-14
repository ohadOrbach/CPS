package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;

import java.util.ArrayList;
import java.util.List;


public class ReceivedConfirmaitnDataEvent {
    private List<ParkingPricesData> pricesDataList;

    public ReceivedConfirmaitnDataEvent(PricesListToConfirm plist) {
        this.pricesDataList = new ArrayList<>();
        List<ParkingPricesData> dataList = plist.getPricesList();
        for (ParkingPricesData p : dataList) {
            pricesDataList.add(p);
//            System.out.println("adding on reciveing\n");
        }
    }

    public List<ParkingPricesData> getPricesDataList() {
        return this.pricesDataList;
    }

}
