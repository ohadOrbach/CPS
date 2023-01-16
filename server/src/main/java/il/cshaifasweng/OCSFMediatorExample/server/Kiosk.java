package il.cshaifasweng.OCSFMediatorExample.server;


import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.OrdersListData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Kiosk {

    public static LocalDateTime convertLocalDateAndStringOfTime(LocalDate localDate, String time){
        String strLocalDate = localDate.toString();
        String strLocalDateTime = strLocalDate + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(strLocalDateTime, formatter);
        return dateTime;
    }

    public String InsertCarIntoParkingLot(String clientId, String carId ,String parkingLotName){
        OrdersListData ordersListData = App.orders.findOrderData(Integer.parseInt(clientId),Integer.parseInt(carId));
        List<OrderData> orderDataList = ordersListData.getOrdersListData();
        boolean foundOrderInThisParkingLotFlag = false;
        for(OrderData orderData: orderDataList){
            // we check if we got order of this client with this car in this parking lot
            if(orderData.getParkingName().compareTo(parkingLotName) == 0){
                // first we convert the order Arrival and Leaving DateTime and it's Time to LocalDateTime object
                LocalDateTime orderArrivalTime = convertLocalDateAndStringOfTime(orderData.getArrivalDate(),orderData.getArrivalTime());
                LocalDateTime orderLeavingTime = convertLocalDateAndStringOfTime(orderData.getLeavingDate(),orderData.getLeavingTime());

                // we check if this order is the order that the client ordered (check the time)
                if(orderArrivalTime.isAfter(LocalDateTime.now())){
                    // update the flag that we found parking order in this parking lot that we are before its date
                    foundOrderInThisParkingLotFlag = true;
                    long noOfMinutes = LocalDateTime.now().until(orderData.getArrivalDate(), ChronoUnit.MINUTES);
                    // we check if the client is 3 minutes before his ordered time, if yes we enter his car
                    if(noOfMinutes <= 3){
                        return "We entered your Car successfully!";
                    }
                }
                // we check if the client is late for his order
                if(orderArrivalTime.isBefore(LocalDateTime.now()) && orderLeavingTime.isAfter(LocalDateTime.now())){
                    // we calculate how many minutes the client is late
                    long noOfMinutes = orderData.getArrivalDate().until(LocalDateTime.now(), ChronoUnit.MINUTES);
                    return "We entered your Car successfully! You are late " + noOfMinutes + " minutes to your order.";
                }
            }
        }
        if(foundOrderInThisParkingLotFlag == true){
            return "You came to soon to your parking order, please come back later";
        }

        return "We didnt find any order in this parking Lot that associates to your id and car id.";
    }
}
