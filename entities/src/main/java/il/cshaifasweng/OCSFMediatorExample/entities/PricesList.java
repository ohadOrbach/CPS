package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;

public class PricesList implements Serializable {
    public List<ParkingPricesData> pricesList;
    public PricesList(List<ParkingPricesData> list){ this.pricesList = list; }

    public void setParkingLotsList(List<ParkingPricesData> list) {
        this.pricesList = list;
    }
    public List<ParkingPricesData> getPricesList() {
        return pricesList;
    }
    public void addPrices(ParkingPricesData parkingPrices){ pricesList.add(parkingPrices);}
}
