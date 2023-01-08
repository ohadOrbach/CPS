package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.OrdersListData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;

import java.util.ArrayList;
import java.util.List;

public class ReceivedOrderList {
    private List<OrderData> orderList;
    private String mode;
    public ReceivedOrderList(OrdersListData ordersListData) {
        this.mode = ordersListData.getMode();
        this.orderList = new ArrayList<>();
        List<OrderData> dataList = ordersListData.getOrdersListData();
        for (OrderData orderData : dataList) {
            orderList.add(orderData);
        }
    }

    public List<OrderData> getOrderListData() {
        return this.orderList;
    }

    public String getMode() {
        return this.mode;
    }

    public ArrayList<String> getInfo() {
        ArrayList<String> info = new ArrayList<>();
        int i = 1;
        for(OrderData orderData: orderList){
            info.add(i++ + "." + " Order id: " + orderData.getOrderId() + " ,Car Number: " + orderData.getCarNumber() + " Expected Arrival Date: "
                    + orderData.getArrivalDate().toString() + " ,From:" + orderData.getLeavingTime()
                    + " Expected Leaving Date: " + orderData.getLeavingDate().toString() + " ,Until: " + orderData.getLeavingTime());
        }
        return info;
    }
}
