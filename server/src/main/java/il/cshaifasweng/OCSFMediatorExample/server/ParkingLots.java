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
        ParkingPrices prices1 = new ParkingPrices(1, 7, 5, namal1);
        namal1.setParkingPrices(prices1);
        ParkingLot namal2 = new ParkingLot("namal2", 2, 10);
        App.session.save(namal2);
        ParkingPrices prices2 = new ParkingPrices(2, 8, 7, namal2);
        namal2.setParkingPrices(prices2);
        ParkingLot namal3 = new ParkingLot("namal3", 3, 20);
        App.session.save(namal3);
        ParkingPrices prices3 = new ParkingPrices(3, 10, 7, namal3);
        namal3.setParkingPrices(prices3);
        ParkingLot namal4 = new ParkingLot("namal4", 4, 50);
        ParkingPrices prices4 = new ParkingPrices(4, 11, 8, namal4);
        namal4.setParkingPrices(prices4);
        ParkingLot namal5 = new ParkingLot("namal5", 5, 5);
        ParkingPrices prices5 = new ParkingPrices(5, 9, 8,namal4);
        namal5.setParkingPrices(prices5);
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
//        System.out.println("all in server");
        App.SafeCommit();
    }

    public void pullParkingLots() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
        query.from(ParkingLot.class);
        List<ParkingLot> data = App.session.createQuery(query).getResultList();
        parkingLots.clear();
        parkingLots.addAll(data);
//        System.out.println("add parking " + data.size() + "list size: " + parkingLots.size());
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

    public void addParkingLot(String name, int rowNum, int rowSize) {
        App.SafeStartTransaction();
        ParkingLot parkingLot = new ParkingLot(name, rowNum, rowSize);
        App.session.save(parkingLot);
        App.session.flush();
        App.SafeCommit();

        parkingLots.add(parkingLot);
    }

    public ParkingLotData getParkingLotData(ParkingLot parkingLot)
    {
        return new ParkingLotData(parkingLot.getParkingLotId(), parkingLot.getRows(), parkingLot.getSize());
    }

    public ParkingLotListData getParkingLotList() {
        List<ParkingLotData> dataList = new ArrayList<>();
        System.out.println("in this");
        for(ParkingLot parkingLot: parkingLots){
            ParkingLotData parkingLotData = getParkingLotData(parkingLot);
            System.out.println("add parking " + parkingLotData.getParkingLotId());
            dataList.add(parkingLotData);
        }
        return new ParkingLotListData(dataList);
    }

    public PricesList getParkingLotsPrices() {
        List<ParkingPricesData> dataList = new ArrayList<>();
        for(int i = 0; i < parkingLots.size(); i++){
            ParkingPricesData parkingPricesData = parkingLots.get(i).getAllPricesData();
            dataList.add(parkingPricesData);
//            System.out.println("add parking " + parkingPricesData.getParkingLotId() + " ,price: " + parkingPricesData.getParkingPrice());
        }
        return new PricesList(dataList);
    }
}
