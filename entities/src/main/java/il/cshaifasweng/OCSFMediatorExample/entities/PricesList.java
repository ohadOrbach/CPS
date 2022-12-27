package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PricesList implements Serializable {
    public List<ParkingPricesData> pricesList;
    public PricesList(){ this.pricesList = new ArrayList<>(); }

    public PricesList(List<ParkingPricesData> dataList){
        this.pricesList = new ArrayList<>();
        for(ParkingPricesData parkingPrices: dataList){
            pricesList.add(parkingPrices);
        }

    }
    public void setParkingLotsList(List<ParkingPricesData> list) {
        this.pricesList = list;
    }
    public List<ParkingPricesData> getPricesList() {
        return pricesList;
    }
    public void addPrices(ParkingPricesData parkingPrices){ pricesList.add(parkingPrices);}
}
