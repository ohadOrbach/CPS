package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrdersListData implements Serializable {
    public List<OrderData> ordersListData;

    //if send for cancellation request mode == "cancellation" else (for tracking)
    String mode;

    public OrdersListData() {
        this.ordersListData = new ArrayList<OrderData>();
    }

    public OrdersListData(List<OrderData> dataList) {
        this.ordersListData = new ArrayList<OrderData>();
        for (OrderData orderData : dataList) {
            ordersListData.add(orderData);
        }
    }

    public void remove(OrderData orderData) {
        ordersListData.remove(ordersListData.remove(orderData));
    }

    public List<OrderData> getOrdersListData() {
        return this.ordersListData;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
