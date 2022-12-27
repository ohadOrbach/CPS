package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingLots {
    public List<ParkingLot> parkingLots;

    public ParkingLots() {
        parkingLots = new ArrayList<>();

    }

    public void generateParkingLots() {
        App.SafeStartTransaction();
        Random random = new Random();
        ParkingLot namal1 = new ParkingLot("namal1", 1, 5);
        App.session.save(namal1);
        ParkingPrices prices1 = new ParkingPrices(1, 5, 7, namal1);
        namal1.setParkingPrices(prices1);
        ParkingLot namal2 = new ParkingLot("namal2", 2, 10);
        App.session.save(namal2);
        ParkingPrices prices2 = new ParkingPrices(1, 5, 7, namal2);
        namal1.setParkingPrices(prices2);
        ParkingLot namal3 = new ParkingLot("namal3", 3, 20);
        App.session.save(namal3);
        ParkingPrices prices3 = new ParkingPrices(1, 5, 7, namal3);
        namal1.setParkingPrices(prices3);
        ParkingLot namal4 = new ParkingLot("namal4", 4, 50);
        App.session.save(namal4);
        ParkingPrices prices4 = new ParkingPrices(1, 5, 7, namal4);
        namal1.setParkingPrices(prices4);
        ParkingLot namal5 = new ParkingLot("namal5", 5, 5);
        ParkingPrices prices5 = new ParkingPrices(1, 5, 7,namal4);
        namal1.setParkingPrices(prices5);
        App.session.save(namal1);
        App.session.save(prices1);
        App.session.save(namal2);
        App.session.save(prices2);
        App.session.save(namal3);
        App.session.save(prices3);
        App.session.save(namal4);
        App.session.save(prices4);
        App.session.save(namal5);
        App.session.save(prices5);
        App.session.flush();
        App.SafeCommit();
    }

    public void pullParkingLots() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
        query.from(ParkingLot.class);
        List<ParkingLot> data = App.session.createQuery(query).getResultList();
        parkingLots.clear();
        parkingLots.addAll(data);
    }

    public void changePrice(int id, double newPrice, String type) {
        App.SafeStartTransaction();
        ParkingLot temp = new ParkingLot();
        for (ParkingLot pl : parkingLots) {
            if (pl.getParkingLotId() == id) {
                temp = pl;
            }
        }
        temp.setParkingPrice(newPrice, type);
        App.session.save(temp);
        App.session.flush();
        App.SafeCommit();
    }

    public void addParkingLot(String name, int rowNum, int rowSize, double price, double orderedPrice) {
        App.SafeStartTransaction();
        ParkingLot parkingLot = new ParkingLot(name, rowNum, rowSize);
        App.session.save(parkingLot);
        App.session.flush();
        App.SafeCommit();

        parkingLots.add(parkingLot);
    }

    public ParkingLotData getParkingLotData(ParkingLot parkingLot)
    {
        ParkingLotData parkingLotData =
                new ParkingLotData(parkingLot.getParkingLotId(), parkingLot.getRows(), parkingLot.getSize());
        return parkingLotData;
    }

    public ParkingLotListData getParkingLotList() {
        List<ParkingLotData> list = new ArrayList<>();
        for(ParkingLot parkingLot: parkingLots){
            ParkingLotData parkingLotData = getParkingLotData(parkingLot);
            list.add(parkingLotData);
        }
        return new ParkingLotListData(list);
    }

    public PricesList getParkingLotsPrices() {
        App.SafeStartTransaction();
        List<ParkingPricesData> list = new ArrayList<>();
        for(ParkingLot parkingLot: parkingLots){
            ParkingPricesData parkingPrices = parkingLot.getAllPricesData();
            list.add(parkingPrices);
        }
        App.SafeCommit();
        return new PricesList(list);
    }
}