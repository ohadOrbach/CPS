package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Error;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
                parking.setParkingOrder(order);
                parking.setStatus(1);       //this parking is occupied now
                foundParking = true;
                App.session.save(parking);
                App.session.flush();
                break;
            }
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

    private OrdersListData findOrderData(int id, int carNum) {
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

}

