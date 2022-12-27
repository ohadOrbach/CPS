package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.server.ParkingPrices;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesList;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class ReceivedParkingPricesEvent {
    private List<ParkingPricesData> pricesDataList;
    public List<ParkingPricesData> getParkingPrices() { return this.pricesDataList; }


    public ReceivedParkingPricesEvent(PricesList pricesList) {
        this.pricesDataList = new ArrayList<>();
        List<ParkingPricesData> dataList = pricesList.getPricesList();
        for(ParkingPricesData parkingPrices: dataList){
            pricesDataList.add(parkingPrices);
//            System.out.println("adding on reciveing\n");
        }
    }

}
