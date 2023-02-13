package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationListData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReceivedStastisticalInformationEvent {
    private List<StastisticalInformationData> stastisticalInformationDataList;

    public ReceivedStastisticalInformationEvent(StastisticalInformationListData stastisticalInformationList) {
        this.stastisticalInformationDataList = new ArrayList<>();
        List<StastisticalInformationData> dataList = stastisticalInformationList.getStastisticalInformationListData();
        for (StastisticalInformationData stastisticalInformation : dataList) {
            stastisticalInformationDataList.add(stastisticalInformation);
        }
    }

    public List<StastisticalInformationData> getstastisticalInformationDataListDataList() {
        stastisticalInformationDataList.sort((o1, o2)
                -> (Integer.valueOf(o1.getParkingLotId()).compareTo(Integer.valueOf(o2.getParkingLotId()))));   //sort list by parking lot id

        for (StastisticalInformationData stastisticalInformation : stastisticalInformationDataList) {
            System.out.println("heyyyyyy   "  + stastisticalInformation.getParkingLotId());
        }

        return stastisticalInformationDataList;
        //return calcDailyMedianForThisWeek();
        //return calcDailyAvgForThisWeek();
    }

    private List<StastisticalInformationData> calcDailyAvgForThisWeek(){
        LocalDate now = LocalDate.now();
        List<StastisticalInformationData> weeklyAvgStastisticalInformationList = new ArrayList<>();
        StastisticalInformationData i=stastisticalInformationDataList.get(0);    //parking lot id counter
        double sumActualOrders=0;
        double sumCancledOrders =0;
        double sumLateParking =0;
        for(StastisticalInformationData stastisticalInformation : stastisticalInformationDataList){
            if(stastisticalInformation.getParkingLotId() != i.getParkingLotId()){
                //StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),sumActualOrders/7,sumCancledOrders/7,sumLateParking/7);
                StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),sumActualOrders/7,sumCancledOrders/7,sumLateParking/7);
                weeklyAvgStastisticalInformationList.add(s1);
                i = stastisticalInformation;
                sumActualOrders=0;
                sumCancledOrders =0;
                sumLateParking =0;
            }
            if(DAYS.between(stastisticalInformation.getDate(),now) <= 7){
                System.out.println("nahardaDasd");
                sumActualOrders+=stastisticalInformation.getActualOrders();
                sumCancledOrders+=stastisticalInformation.getCanceledOrders();
                sumLateParking+=stastisticalInformation.getParkingLateNum();
            }
        }
        //add last statistical information
        StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),sumActualOrders/7,sumCancledOrders/7,sumLateParking/7);
        weeklyAvgStastisticalInformationList.add(s1);

        return weeklyAvgStastisticalInformationList;
    }


    private List<StastisticalInformationData> calcDailyMedianForThisWeek(){
        LocalDate now = LocalDate.now();
        List<StastisticalInformationData> weeklyAvgStastisticalInformationList = new ArrayList<>();
        List<Double> currentActualOrders = new ArrayList<>();
        List<Double> currentCancledOrders = new ArrayList<>();
        List<Double> currentLateParking = new ArrayList<>();
        StastisticalInformationData i=stastisticalInformationDataList.get(0);    //parking lot id counter
        for(StastisticalInformationData stastisticalInformation : stastisticalInformationDataList){
            if(stastisticalInformation.getParkingLotId() != i.getParkingLotId()){
                StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),currentActualOrders.get((currentActualOrders.size()+1)/2-1),currentCancledOrders.get((currentCancledOrders.size()+1)/2-1),currentLateParking.get((currentLateParking.size()+1)/2-1));
                weeklyAvgStastisticalInformationList.add(s1);
                i = stastisticalInformation;
                currentActualOrders.clear();
                currentCancledOrders.clear();
                currentLateParking.clear();
            }
            if(DAYS.between(stastisticalInformation.getDate(),now) <= 7){
                System.out.println("nahardaDasd");
                currentActualOrders.add(stastisticalInformation.getActualOrders());
                Collections.sort(currentActualOrders);
                currentCancledOrders.add(stastisticalInformation.getCanceledOrders());
                Collections.sort(currentCancledOrders);
                currentLateParking.add(stastisticalInformation.getParkingLateNum());
                Collections.sort(currentLateParking);
            }
        }
        //add last statistical information
        StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),currentActualOrders.get((currentActualOrders.size()+1)/2-1),currentCancledOrders.get((currentCancledOrders.size()+1)/2-1),currentLateParking.get((currentLateParking.size()+1)/2-1));
        weeklyAvgStastisticalInformationList.add(s1);

        return weeklyAvgStastisticalInformationList;

    }


}
