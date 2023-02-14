package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingLots {
    public List<ParkingLot> parkingLots;
    public List<ParkingPrices> parkingPrices;
    public List<StastisticalInformation> stastisticalInformations;


    public ParkingLots() {
        parkingLots = new ArrayList<>();
        parkingPrices = new ArrayList<>();
    }

    public void generateParkingLots() {
        App.SafeStartTransaction();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  //get current day for statistical information
        LocalDate now = LocalDate.now();
        Random random = new Random();
        ParkingLot namal1 = new ParkingLot("namal1", 4);
        App.session.save(namal1);
        ParkingPrices prices1 = new ParkingPrices(1, 7, 5, namal1);
        namal1.setParkingPrices(prices1);
        StastisticalInformation stastisticalInformation1 = new StastisticalInformation(1,now,0,0,0, namal1);    //set statistical information
        namal1.addStastisticalInformation(stastisticalInformation1);
        ParkingLot namal2 = new ParkingLot("namal2", 5);
        App.session.save(namal2);
        ParkingPrices prices2 = new ParkingPrices(2, 8, 7, namal2);
        namal2.setParkingPrices(prices2);
        StastisticalInformation stastisticalInformation2 = new StastisticalInformation(2,now,0,0,0, namal2);    //set statistical information
        namal2.addStastisticalInformation(stastisticalInformation2);
        ParkingLot namal3 = new ParkingLot("namal3", 6);
        App.session.save(namal3);
        ParkingPrices prices3 = new ParkingPrices(3, 10, 7, namal3);
        namal3.setParkingPrices(prices3);
        StastisticalInformation stastisticalInformation3 = new StastisticalInformation(3,now,0,0,0, namal3);    //set statistical information
        namal3.addStastisticalInformation(stastisticalInformation3);
        ParkingLot namal4 = new ParkingLot("namal4", 7);
        ParkingPrices prices4 = new ParkingPrices(4, 11, 8, namal4);
        namal4.setParkingPrices(prices4);
        StastisticalInformation stastisticalInformation4 = new StastisticalInformation(4,now,0,0,0, namal4);    //set statistical information
        namal4.addStastisticalInformation(stastisticalInformation4);
        ParkingLot namal5 = new ParkingLot("namal5", 8);
        ParkingPrices prices5 = new ParkingPrices(5, 9, 8,namal5);
        namal5.setParkingPrices(prices5);
        StastisticalInformation stastisticalInformation5 = new StastisticalInformation(5,now,0,0,0, namal5);    //set statistical information
        namal5.addStastisticalInformation(stastisticalInformation5);

        App.session.save(namal1);
        App.session.save(prices1);
        App.session.save(stastisticalInformation1);
        for(int i=1; i<=4; i++){
            for (int j = 1; j<=3; j++)
                for(int h = 1; h<= 3; h++){
                    Parking parking = new Parking(0,j,i,h,namal1);
                    App.session.save(parking);
                    namal1.addParking(parking);
                }
        }
        App.session.save(namal1);

        App.session.save(namal2);
        App.session.save(prices2);
        App.session.save(stastisticalInformation2);
        for(int i=1; i<=5; i++){
            for (int j = 1; j<=3; j++)
                for(int h = 1; h<= 3; h++){
                    Parking parking = new Parking(0,j,i,h,namal2);
                    App.session.save(parking);
                    namal2.addParking(parking);
                }
        }
        App.session.save(namal2);

        App.session.save(namal3);
        App.session.save(prices3);
        App.session.save(stastisticalInformation3);
        for(int i=1; i<=6; i++){
            for (int j = 1; j<=3; j++)
                for(int h = 1; h<= 3; h++){
                    Parking parking = new Parking(0,j,i,h,namal3);
                    App.session.save(parking);
                    namal3.addParking(parking);
                }
        }
        App.session.save(namal3);

        App.session.save(namal4);
        App.session.save(prices4);
        App.session.save(stastisticalInformation4);
        for(int i=1; i<=7; i++){
            for (int j = 1; j<=3; j++)
                for(int h = 1; h<= 3; h++){
                    Parking parking = new Parking(0,j,i,h,namal4);
                    App.session.save(parking);
                    namal4.addParking(parking);
                }
        }
        App.session.save(namal4);

        App.session.save(namal5);
        App.session.save(prices5);
        App.session.save(stastisticalInformation5);
        for(int i=1; i<=8; i++){
            for (int j = 1; j<=3; j++)
                for(int h = 1; h<= 3; h++){
                    Parking parking = new Parking(0,j,i,h,namal5);
                    App.session.save(parking);
                    namal5.addParking(parking);
                }
        }
        App.session.save(namal5);

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

    public void pullParkingPrices() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<ParkingPrices> query = builder.createQuery(ParkingPrices.class);
        query.from(ParkingPrices.class);
        List<ParkingPrices> data = App.session.createQuery(query).getResultList();
        parkingPrices.clear();
        parkingPrices.addAll(data);
    }

    public void pullStatisticalInformation() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<StastisticalInformation> query = builder.createQuery(StastisticalInformation.class);
        query.from(StastisticalInformation.class);
        List<StastisticalInformation> data = App.session.createQuery(query).getResultList();
        stastisticalInformations.clear();
        stastisticalInformations.addAll(data);
    }

    public ParkingPricesData askToChangePrice(int id, String type, double newPrice) {
        ParkingPrices temp = new ParkingPrices();
        for (ParkingPrices pl : parkingPrices) {
            if (pl.getParkingLotId() == id) {
                temp = pl;
            }
        }
        ParkingPrices temp2 = new ParkingPrices(temp.getParkingLotId(), temp.getParkingPrice(), temp.getOrderedParkingPrice(), temp.getParkingLot());
        if (type.equals("Casual")) {
            temp2.setParkingPrice(newPrice);
        } else {
            temp2.setOrderedParkingPrice(newPrice);
        }
        ParkingPricesData newAsk = new ParkingPricesData(id, temp2.getParkingPrice(), temp2.getOrderedParkingPrice());
        return newAsk;
    }

    public ParkingLot getParkingLot(int id){
        for (ParkingPrices pl : parkingPrices) {
            if (pl.getParkingLotId() == id) {
               return pl.getParkingLot();
            }
        }
        return null;
    }

    public void changePrice(int id, String type, double newPrice) {
        App.SafeStartTransaction();
        ParkingPrices temp = new ParkingPrices();
        for (ParkingPrices pl : parkingPrices) {
            if (pl.getParkingLotId() == id) {
                temp = pl;
            }
        }
        if (type.equals("Casual")){temp.setParkingPrice(newPrice);}
        else{temp.setOrderedParkingPrice(newPrice);}
        App.session.save(temp);
        App.session.flush();
        App.SafeCommit();
    }

    public void addParkingLot(String name, int rowNum, int rowSize) {
        App.SafeStartTransaction();
        ParkingLot parkingLot = new ParkingLot(name, rowNum);
        App.session.save(parkingLot);
        App.session.flush();
        App.SafeCommit();

        parkingLots.add(parkingLot);
    }

    public void changeParkingStatus(String id, int row, int col, int lvl, String status) {
        App.SafeStartTransaction();
        Parking temp;
        for (ParkingLot parkLot : parkingLots) { //finding parking lot
            if (parkLot.getName().equals(id)) {
                for (Parking park: parkLot.getParkings()) {    // finding parking Space
                    if ((park.getRow() == row) & (park.getColumn() == col) & (park.getDepth() == lvl)){
                        temp = park;
                        switch (status) {
                            case "Working":
                                temp.setStatus(0);
                                break;
                            case "Faulty":
                                temp.setStatus(-1);
                                break;
                            case "Reserved":
                                temp.setStatus(2);
                                break;
                        }
                        App.session.save(temp);
                        App.session.flush();
                        App.SafeCommit();
                        return;
                    }
                }
            }
        }
    }


    public ParkingLotListData getParkingLotList() {
        List<ParkingLotData> dataList = new ArrayList<>();
        for(ParkingLot parkingLot: parkingLots){
            ParkingLotData parkingLotData = parkingLot.getParkingLotData();
            System.out.println("add parking " + parkingLotData.getParkingLotId());
            dataList.add(parkingLotData);
        }
        return new ParkingLotListData(dataList);
    }

    public List<ParkingLot> getParkingLots() {
        return this.parkingLots;
    }

    public PricesList getParkingLotsPrices() {
        List<ParkingPricesData> dataList = new ArrayList<>();
        for(int i = 0; i < parkingLots.size(); i++){
            ParkingPricesData parkingPricesData = parkingLots.get(i).getAllPricesData();
            dataList.add(parkingPricesData);
        }
        return new PricesList(dataList);
    }
    public int findParkingLotId(String parkingLotName){
        for(ParkingLot parkingLot: parkingLots)
        {
            if(parkingLot.getName()==parkingLotName)
            {
                return parkingLot.getParkingLotId();
            }
        }
        return -1;  //we didnt find it
    }

    ParkingLot getParkingLotByName(String name)
    {
        for(ParkingLot parkingLot : parkingLots)
        {
            if (name.equals(parkingLot.getName()))
            {
                return parkingLot;
            }
        }

        return null;
    }
}