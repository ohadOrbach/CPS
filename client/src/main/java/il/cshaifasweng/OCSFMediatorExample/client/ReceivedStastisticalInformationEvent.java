package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationListData;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            //return calcDailyAvgForThisWeek();
    }

    private List<StastisticalInformationData> calcDailyAvgForThisWeek(){
        LocalDate now = LocalDate.now();
        List<StastisticalInformationData> weeklyAvgStastisticalInformationList = new ArrayList<>();
        StastisticalInformationData currentAvgDate = new StastisticalInformationData();
        currentAvgDate.setParkingLotId(stastisticalInformationDataList.get(0).getParkingLotId());
        currentAvgDate.setId(0);    //doesnt matter
        currentAvgDate.setName(stastisticalInformationDataList.get(0).getName());

        int idCounter = 0;
        int i=currentAvgDate.getParkingLotId();    //parking lot id counter
        int sumActualOrders =0;
        int devisionActualOrders = 1;
        int sumCancledOrders =0;
        int devisionCancledOrders = 1;
        int sumLateParking =0;
        int devisionLateParking = 1;


        for (StastisticalInformationData stastisticalInformation : stastisticalInformationDataList) {
            System.out.println("heyyyyyy   "  + stastisticalInformation.getParkingLotId());
            System.out.println("heyyyyyy   "  + currentAvgDate.getParkingLotId());
            System.out.println("heyyyyyy   "  + currentAvgDate.getName());
            System.out.println("heyyyyyy   "  + currentAvgDate.getId());


            //check if we currenly in the list at parking lot number i
            if(stastisticalInformation.getParkingLotId() == i) {
                System.out.println("we innnnnn   "  + i);

                //we get all the data from this week
                if (DAYS.between(now, stastisticalInformation.getDate()) <= 7) {
                    System.out.println("less the 5 daysssss   "  + i);

                    sumActualOrders += stastisticalInformation.getActualOrders();
                    devisionActualOrders++;
                    sumCancledOrders += stastisticalInformation.getCanceledOrders();
                    devisionCancledOrders++;
                    sumLateParking += stastisticalInformation.getParkingLateNum();
                    devisionLateParking++;
                }
            }
            //we finished to calc avg for the current parking lot
            else
            {
                idCounter++;    //update Id Counter
                i = stastisticalInformation.getParkingLotId(); //update the current parking lot id we are working on
                //check we dont devide by 0
                if(devisionActualOrders-1==0)
                    currentAvgDate.setActualOrders(0);
                else
                    currentAvgDate.setActualOrders(sumActualOrders/(devisionActualOrders-1));   //set avg for actual orders
                if(devisionCancledOrders-1==0)
                    currentAvgDate.setCanceledOrders(0);
                else
                    currentAvgDate.setCanceledOrders(sumCancledOrders/(devisionCancledOrders-1));   //set avg for actual orders
                if(devisionLateParking-1==0)
                    currentAvgDate.setParkingLateNum(0);
                else
                    currentAvgDate.setParkingLateNum(sumLateParking/(devisionLateParking-1));   //set avg for actual orders

                System.out.println("heyyyyyy   "  + currentAvgDate.getActualOrders());
                System.out.println("heyyyyyy   "  + currentAvgDate.getCanceledOrders());
                System.out.println("heyyyyyy   "  + currentAvgDate.getParkingLateNum());


                weeklyAvgStastisticalInformationList.add(currentAvgDate);   //add the avg for this parking lot

                //initilize
                currentAvgDate = new StastisticalInformationData();
                currentAvgDate.setParkingLotId(stastisticalInformation.getParkingLotId());
                currentAvgDate.setId(idCounter);    //doesnt matter
                currentAvgDate.setName(stastisticalInformation.getName());
                sumActualOrders =0;
                devisionActualOrders = 1;
                sumCancledOrders =0;
                devisionCancledOrders = 1;
                sumLateParking =0;
                devisionLateParking = 1;
                if (DAYS.between(now, stastisticalInformation.getDate()) <= 7) {
                    System.out.println("less the 5 daysssss   "  + i);

                    sumActualOrders += stastisticalInformation.getActualOrders();
                    devisionActualOrders++;
                    sumCancledOrders += stastisticalInformation.getCanceledOrders();
                    devisionCancledOrders++;
                    sumLateParking += stastisticalInformation.getParkingLateNum();
                    devisionLateParking++;
                }
            }
        }
        //insert last item
        if(devisionActualOrders-1==0)
            currentAvgDate.setActualOrders(0);
        else
            currentAvgDate.setActualOrders(sumActualOrders/(devisionActualOrders-1));   //set avg for actual orders
        if(devisionCancledOrders-1==0)
            currentAvgDate.setCanceledOrders(0);
        else
            currentAvgDate.setCanceledOrders(sumCancledOrders/(devisionCancledOrders-1));   //set avg for actual orders
        if(devisionLateParking-1==0)
            currentAvgDate.setParkingLateNum(0);
        else
            currentAvgDate.setParkingLateNum(sumLateParking/(devisionLateParking-1));   //set avg for actual orders

        System.out.println("heyyyyyy   "  + currentAvgDate.getActualOrders());
        System.out.println("heyyyyyy   "  + currentAvgDate.getCanceledOrders());
        System.out.println("heyyyyyy   "  + currentAvgDate.getParkingLateNum());


        weeklyAvgStastisticalInformationList.add(currentAvgDate);   //add the avg for this parking lot


        return weeklyAvgStastisticalInformationList;
    }


    private List<StastisticalInformationData> calcDailyMedianForThisWeek(){
        LocalDate now = LocalDate.now();
        List<StastisticalInformationData> weeklyAvgStastisticalInformationList = new ArrayList<>();
        List<Integer> currentActualOrders = new ArrayList<>();
        List<Integer> currentCancledOrders = new ArrayList<>();
        List<Integer> currentLateParking = new ArrayList<>();
        StastisticalInformationData currentAvgData = new StastisticalInformationData();
        currentAvgData.setParkingLotId(stastisticalInformationDataList.get(0).getParkingLotId());   //initilize
        currentAvgData.setName(stastisticalInformationDataList.get(0).getName());   //initilize


        for (StastisticalInformationData stastisticalInformation : stastisticalInformationDataList) {

            //check if we currenly in the list at parking lot number i
            if(stastisticalInformation.getParkingLotId() == currentAvgData.getParkingLotId()) {
                //we get all the data from this week
                if (DAYS.between(now, stastisticalInformation.getDate()) <= 7) {
                    currentActualOrders.add(stastisticalInformation.getActualOrders());
                    currentCancledOrders.add(stastisticalInformation.getCanceledOrders());
                    currentLateParking.add(stastisticalInformation.getParkingLateNum());
                }
            }
            //we finished to calc mean for the current parking lot
            else
            {

                //find median
                Collections.sort(currentActualOrders);
                Collections.sort(currentCancledOrders);
                Collections.sort(currentLateParking);
                if (currentActualOrders.size() % 2 == 0)
                    currentAvgData.setActualOrders((currentActualOrders.get(currentActualOrders.size()/2) + currentActualOrders.get(currentActualOrders.size()/2 - 1))/2);
                else
                    currentAvgData.setActualOrders(currentActualOrders.get(currentActualOrders.size()/2));
                if (currentCancledOrders.size() % 2 == 0)
                    currentAvgData.setActualOrders((currentCancledOrders.get(currentCancledOrders.size()/2) + currentCancledOrders.get(currentCancledOrders.size()/2 - 1))/2);
                else
                    currentAvgData.setActualOrders(currentCancledOrders.get(currentCancledOrders.size()/2));
                if (currentLateParking.size() % 2 == 0)
                    currentAvgData.setActualOrders((currentLateParking.get(currentLateParking.size()/2) + currentLateParking.get(currentLateParking.size()/2 - 1))/2);
                else
                    currentAvgData.setActualOrders(currentLateParking.get(currentLateParking.size()/2));


                weeklyAvgStastisticalInformationList.add(currentAvgData);   //add the avg for this parking lot

                //initilize
                currentActualOrders = new ArrayList<>();
                currentCancledOrders = new ArrayList<>();
                currentLateParking = new ArrayList<>();
                currentAvgData = new StastisticalInformationData();
                currentAvgData.setParkingLotId(stastisticalInformation.getParkingLotId());   //initilize
                currentAvgData.setName(stastisticalInformation.getName());   //initilize
            }
        }
        //insert last item
        //find median
        Collections.sort(currentActualOrders);
        Collections.sort(currentCancledOrders);
        Collections.sort(currentLateParking);
        if (currentActualOrders.size() % 2 == 0)
            currentAvgData.setActualOrders((currentActualOrders.get(currentActualOrders.size()/2) + currentActualOrders.get(currentActualOrders.size()/2 - 1))/2);
        else
            currentAvgData.setActualOrders(currentActualOrders.get(currentActualOrders.size()/2));
        if (currentCancledOrders.size() % 2 == 0)
            currentAvgData.setActualOrders((currentCancledOrders.get(currentCancledOrders.size()/2) + currentCancledOrders.get(currentCancledOrders.size()/2 - 1))/2);
        else
            currentAvgData.setActualOrders(currentCancledOrders.get(currentCancledOrders.size()/2));
        if (currentLateParking.size() % 2 == 0)
            currentAvgData.setActualOrders((currentLateParking.get(currentLateParking.size()/2) + currentLateParking.get(currentLateParking.size()/2 - 1))/2);
        else
            currentAvgData.setActualOrders(currentLateParking.get(currentLateParking.size()/2));


        weeklyAvgStastisticalInformationList.add(currentAvgData);   //add the avg for this parking lot


        return weeklyAvgStastisticalInformationList;
    }


}
