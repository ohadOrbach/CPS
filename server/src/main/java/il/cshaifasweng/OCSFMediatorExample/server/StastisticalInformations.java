package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StastisticalInformations {
    public List<StastisticalInformation> stastisticalInformations;
    public StastisticalInformations()
    {
        stastisticalInformations=new ArrayList<StastisticalInformation>();
    }
    public void pullStastisticalInformationFromDB()
    {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<StastisticalInformation> query = builder.createQuery(StastisticalInformation.class);
        query.from(StastisticalInformation.class);
        List<StastisticalInformation> data = App.session.createQuery(query).getResultList();
        stastisticalInformations.clear();
        stastisticalInformations.addAll(data);
    }


    public void addStastisticalInformation(int parkingLotId , String name , LocalDate date , int actualOrder , int canceledOrders , int parkingLateNum) {
        App.SafeStartTransaction();
        StastisticalInformation stastisticalInformation = new StastisticalInformation(parkingLotId ,name,date, actualOrder, canceledOrders, parkingLateNum);
        App.session.save(stastisticalInformation);
        App.session.flush();
        App.SafeCommit();

        stastisticalInformations.add(stastisticalInformation);
    }

    public StastisticalInformationData getStastisticalInformationData(StastisticalInformation stastisticalInformation)
    {
        return new StastisticalInformationData(stastisticalInformation.getStatisticId(), stastisticalInformation.getParkingLotId() , stastisticalInformation.getName() ,stastisticalInformation.getDate(), stastisticalInformation.getActualOrders(), stastisticalInformation.getCanceledOrders(),stastisticalInformation.getParkingLateNum());
    }

    public StastisticalInformationListData getStastisticalInformationList() {
        List<StastisticalInformationData> dataList = new ArrayList<>();
        for(StastisticalInformation stastisticalInformation: stastisticalInformations){
            StastisticalInformationData stastisticalInformationData = getStastisticalInformationData(stastisticalInformation);
            dataList.add(stastisticalInformationData);
        }
        return new StastisticalInformationListData(dataList);
    }

    //method to add today statistics information
    public void addStastisticalInformationForCancledOrder(OrdersListData ordersListData){
        if(ordersListData.getOrdersListData().size() < 1)
            return;
        App.SafeStartTransaction();
        LocalDate now = LocalDate.now();
        List<OrderData> list = ordersListData.getOrdersListData();
        int parkingLotId = App.parkinglots.findParkingLotId(list.get(0).getParkingName());
        int cancledOrderCounter = 0; //counter for specific cancelation for parking lot
        for(OrderData orderData: list) {/*
             int tempParkingLotId = App.parkinglots.findParkingLotId(orderData.getParkingName());
             //we got diffrent parking lot id
             if(tempParkingLotId != parkingLotId)
             {
                 parkingLotId = tempParkingLotId;
                 StastisticalInformation stastisticalInformation = new StastisticalInformation(parkingLotId ,orderData.getParkingName(),now, 0, cancledOrderCounter, 0);
                 System.out.println("stastisticalInformation in server:\n" + "added " + cancledOrderCounter + " to this parking lot id");
                 App.session.save(stastisticalInformation);
                 App.session.flush();
                 stastisticalInformations.add(stastisticalInformation);
                 cancledOrderCounter = 1;
             }
             else
                 cancledOrderCounter++;     //we got another cancled order for this parking lot*/
            int tempParkingLotId = App.parkinglots.findParkingLotId(orderData.getParkingName());
            System.out.println(tempParkingLotId);
            int statisticalInformationIndex = findStatisticalInformation(tempParkingLotId,now);
            System.out.println(statisticalInformationIndex);
            if(statisticalInformationIndex == -1)
            {
                StastisticalInformation stastisticalInformation = new StastisticalInformation(parkingLotId ,orderData.getParkingName(),now, 0, 1, 0);
                System.out.println("stastisticalInformation in server:\n" + "added " + cancledOrderCounter + " to this parking lot id");
                App.session.save(stastisticalInformation);
                App.session.flush();
                stastisticalInformations.add(stastisticalInformation);
            }
            else{
                int currentCancledOrders = stastisticalInformations.get(statisticalInformationIndex).getCanceledOrders();
                stastisticalInformations.get(statisticalInformationIndex).setCanceledOrders(currentCancledOrders+1);
                App.session.flush();
            }
        }
        //add last statisticsInformation
        /*
        StastisticalInformation stastisticalInformation = new StastisticalInformation(parkingLotId ,list.get(list.size()-1).getParkingName(),now, 0, cancledOrderCounter, 0);
        System.out.println("stastisticalInformation in server:\n" + "added " + cancledOrderCounter + " to this parking lot id");
        App.session.save(stastisticalInformation);
        App.session.flush();
        stastisticalInformations.add(stastisticalInformation);
        */
        App.SafeCommit();
    }

    //test!!!!!
    private int findStatisticalInformation(int parkingLotId, LocalDate date){
        int index=0;
        System.out.println("find func " + parkingLotId);
        System.out.println("find func " + date);

        for(StastisticalInformation stastisticalInformation : stastisticalInformations){
            System.out.println("loop " + stastisticalInformation.getParkingLotId());
            System.out.println("loop " + stastisticalInformation.getDate());

            if((stastisticalInformation.getParkingLotId() == parkingLotId) && (stastisticalInformation.getDate().compareTo(date) == 0)){
                return index;
            }
            index++;
        }
        //we didnt find the stastisticalInformation
        return -1;
    }




}
