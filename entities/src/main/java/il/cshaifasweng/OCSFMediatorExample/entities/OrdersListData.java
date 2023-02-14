package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrdersListData implements Serializable {
    public List<OrderData> ordersListData;

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

}
