package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Error;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Orders {
    public List<ParkingOrder> ordersList;

    public Orders() {
        ordersList = new ArrayList<>();
    }



    public String addOrder(OrderData orderData) {
        App.SafeStartTransaction();
        ParkingOrder order = new ParkingOrder(orderData.getId(), orderData.getCarNumber(), orderData.getArrivalDate(), orderData.getArrivalTime(), orderData.getLeavingDate(), orderData.getLeavingTime(), orderData.getEmail(), orderData.getAdv(), orderData.getParkingName());
        App.session.save(order);
        App.session.flush();
        String parkingLotName = orderData.getParkingName();
        ParkingLot parkingLot = App.parkinglots.getParkingLotByName(parkingLotName);
        boolean foundParking = false;
        for(Parking parking: parkingLot.getParkings()) {
            if(parking.getStatus() == 0){
                //this parking is empty, so we set this order to be in this parking
                parking.addParkingOrder(order);
                parking.setStatus(1);       //this parking is occupied now
                foundParking = true;
                App.session.save(parking);
                App.session.flush();
                order.setParking(parking);
                App.session.save(order);
                App.session.flush();
                break;
            }
        }
        // if we didn't find an empty parking space
        if(foundParking == false){
            // if its advanced order, we might be able to find empty parking space in those hours
            //if(orderData.getAdv().compareTo("true") == 0){
                for(Parking parking: parkingLot.getParkings()){
                    // we check if this parking is occupied in these date and hours
                    List<ParkingOrder> parkingOrders = parking.getParkingOrder();
                    boolean collusionFlag = false;
                    for(ParkingOrder parkingOrder: parkingOrders){
                        // first we convert the order and parking Arrival and Leaving DateTime and it's Time to LocalDateTime object
                        LocalDateTime orderArrivalTime = Kiosk.convertLocalDateAndStringOfTime(orderData.getArrivalDate(),orderData.getArrivalTime());
                        LocalDateTime orderLeavingTime = Kiosk.convertLocalDateAndStringOfTime(orderData.getLeavingDate(),orderData.getLeavingTime());
                        LocalDateTime parkingArrivalTime = Kiosk.convertLocalDateAndStringOfTime(parkingOrder.getArrivalDate(),parkingOrder.getArrivalTime());
                        LocalDateTime parkingLeavingTime = Kiosk.convertLocalDateAndStringOfTime(parkingOrder.getLeavingDate(),parkingOrder.getLeavingTime());

                        // we check if there is collusion between these 2 orders
                        if(((orderArrivalTime.isAfter(parkingArrivalTime) || orderArrivalTime.isEqual(parkingArrivalTime)) && (orderArrivalTime.isBefore(parkingLeavingTime) || orderArrivalTime.isEqual(parkingLeavingTime)))
                         || ((orderLeavingTime.isAfter(parkingArrivalTime) || orderLeavingTime.isEqual(parkingArrivalTime)) && (orderLeavingTime.isBefore(parkingLeavingTime) || orderLeavingTime.isEqual(parkingLeavingTime)))
                        || ((orderArrivalTime.isBefore(parkingArrivalTime) || orderArrivalTime.isEqual(parkingArrivalTime)) && (orderLeavingTime.isAfter(parkingLeavingTime) || orderLeavingTime.isEqual(parkingLeavingTime))))
                        {
                            // we found collusion with this order, we break and move to next parking
                            collusionFlag = true;
                            break;
                        }
                    }
                    if(collusionFlag == false){
                        // we didnt find any collusion in this parking spaces orders so we can add this order to this parking space
                        parking.addParkingOrder(order);
                        App.session.save(parking);
                        App.session.flush();
                        order.setParking(parking);
                        App.session.save(order);
                        App.session.flush();
                        foundParking = true;
                        break;
                    }
                }
            //}
        }

        App.SafeCommit();
        ordersList.add(order);
        if(foundParking == false)
            return "error, we didnt find parking in this ParkingLot";

        return "Your order has been successfully received! Thank you and happy parking";
    }

    public OrderData getOrderData(ParkingOrder order) { return order.getOrderData(); }

    public void pullOrders() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<ParkingOrder> query = builder.createQuery(ParkingOrder.class);
        query.from(ParkingOrder.class);
        List<ParkingOrder> data = App.session.createQuery(query).getResultList();
        ordersList.clear();
        ordersList.addAll(data);
    }


    public OrdersListData trackOrder(TrackingOrderData trackingOrderData) {
        OrdersListData trackingOrders = findOrderData(trackingOrderData.getId(), trackingOrderData.getCarNum());
        return trackingOrders;
    }


    public OrdersListData findCancelOrder(CancelOrderData cancelOrderData) {
        App.SafeStartTransaction();
        OrdersListData cancelOrders = findOrderData(cancelOrderData.getId(), cancelOrderData.getCarNum());
        App.SafeCommit();
        return cancelOrders;
    }

    public OrdersListData findOrderData(int id, int carNum) {
        List<OrderData> list = new ArrayList<>();
        System.out.println("in find order data\n");
        for(ParkingOrder parkingOrder: ordersList){
            if(parkingOrder.getUserId() == id && parkingOrder.getCarNumber() == carNum)
                list.add(parkingOrder.getOrderData());
        }
        System.out.println("return list tracking\n");
        System.out.println(list);
        return new OrdersListData(list);
    }

    // find orders and delete - return fine massage.
    public Object deleteOrders(OrdersListData ordersListData) throws ParseException {
        App.SafeStartTransaction();
        List<OrderData> ordersList1 = ordersListData.getOrdersListData();
        double fine = 0;

        // fine each order and calculate her fine.
        for(OrderData orderData: ordersList1){
            ParkingOrder parkingOrder = findOrderByData(orderData);
            if(parkingOrder == null){
                return new Error("Error! order: id - " + orderData.getId() + ", car number - "+ orderData.getCarNumber()  + "not found");
            }
            fine += calculateFine(parkingOrder);
            ordersList1.remove(parkingOrder);
            ordersList.remove(parkingOrder);
            App.session.remove(parkingOrder);
            App.session.flush();
        }
        App.SafeCommit();
        return new Message("Orders have been successfully deleted, The fine is: " + fine);
    }

    // calculate fine for an order cancellation.
    private double calculateFine(ParkingOrder parkingOrder) throws ParseException {
        // get arrival time and date - in format below
        SimpleDateFormat diffFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date arrival = diffFormat.parse(parkingOrder.getArrivalDate().toString() + " " + parkingOrder.getArrivalTime() + ":00");
        Date current = diffFormat.parse(LocalDate.now().toString()+ " " + LocalDateTime.now().getHour() +
                ":" + LocalDateTime.now().getMinute() + ":00");
        System.out.println("in fine calcul\n");
        // calculate diff in hours.
        long difference_In_Time = current.getTime() - arrival.getTime();
        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

        // 10% fine if cancel 3 hours before.
        if(difference_In_Hours >= 3)
            return parkingOrder.getExpectedPayment() * 0.1;

        // 50% fine if cancel 1-3 hours before.
        if(difference_In_Hours < 3 && difference_In_Hours > 1)
            return parkingOrder.getExpectedPayment() * 0.5;

        // 100% fine if cancel 1 hour or less before.
        return parkingOrder.getExpectedPayment();
    }


    private ParkingOrder findOrderByData(OrderData orderData) {
        for (ParkingOrder parkingOrder : ordersList) {
            if (parkingOrder.getUserId() == orderData.getId()
                    && parkingOrder.getCarNumber() == orderData.getCarNumber()) {
                return parkingOrder;
            }
        }
        return null;
    }

    public int findIndexByData(OrderData orderData) {
        int index = 0;
        for (ParkingOrder parkingOrder : ordersList) {
            if (parkingOrder.getUserId() == orderData.getId() && parkingOrder.getCarNumber() == orderData.getCarNumber()) {
                if(parkingOrder.getArrivalDate().compareTo(orderData.getArrivalDate()) == 0 && parkingOrder.getLeavingDate().compareTo(parkingOrder.getLeavingDate()) == 0){
                    if(parkingOrder.getArrivalTime().compareTo(orderData.getArrivalTime()) == 0 && parkingOrder.getLeavingTime().compareTo(parkingOrder.getLeavingTime()) == 0){
                        return index;
                    }
                }
            }
            index++;
        }
        return -1;
    }

}

