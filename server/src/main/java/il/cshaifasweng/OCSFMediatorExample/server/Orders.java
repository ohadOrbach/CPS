package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Error;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    //TODO add fine calculation and charge
    public OrdersListData findCancelOrder(CancelOrderData cancelOrderData) {
        App.SafeStartTransaction();
        OrdersListData cancelOrders = findOrderData(cancelOrderData.getId(), cancelOrderData.getCarNum());
        App.SafeCommit();
        return cancelOrders;
    }

    public OrdersListData findOrderData(int id, int carNum) {
        List<OrderData> list = new ArrayList<>();
        for(ParkingOrder parkingOrder: ordersList){
            if(parkingOrder.getUserId() == id && parkingOrder.getCarNumber() == carNum)
                list.add(parkingOrder.getOrderData());
        }
        return new OrdersListData(list);
    }

    public Object deleteOrders(OrdersListData ordersListData) {
        App.SafeStartTransaction();
        List<OrderData> ordersList1 = ordersListData.getOrdersListData();
        for(OrderData orderData: ordersList1){
            ParkingOrder parkingOrder = findOrderByData(orderData);
            if(parkingOrder == null){
                return new Error("Error! order: id - " + orderData.getId() + ", car number - "+ orderData.getCarNumber()  + "not found");
            }
            ordersList1.remove(parkingOrder);
            ordersList.remove(parkingOrder);
            App.session.remove(parkingOrder);
            App.session.flush();
        }
        App.SafeCommit();
        return new Message("Orders have been successfully deleted");
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

