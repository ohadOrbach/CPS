package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class SimpleClient extends AbstractClient {

    private static SimpleClient client = null;

    private SimpleClient(String host, int port) {
        super(host, port);
    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("localhost", 3000);
        }
        return client;
    }

    public static SimpleClient getClient(String ip, int port) {
        if (client == null) {
            client = new SimpleClient(ip, port);
        }
        return client;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        System.out.format("i got object from %s class\n", msg.getClass().getSimpleName());
        if (msg.getClass().equals(Warning.class)) {
            Platform.runLater(() -> EventBus.getDefault().post(new WarningEvent((Warning) msg)));
        } else if (msg.getClass().equals(ParkingLotListData.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(new ReceivedParkingLotListEvent((ParkingLotListData) msg)));
        } else if (msg.getClass().equals(PricesList.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(new ReceivedParkingPricesEvent((PricesList) msg)));
        } else if (msg.getClass().equals(StastisticalInformationListData.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(new ReceivedStastisticalInformationEvent((StastisticalInformationListData) msg)));
        } else if (msg.getClass().equals(ParkingListData.class)) {
            System.out.format("i got parkings list");
            Platform.runLater(() -> EventBus.getDefault().
                    post(new ReceivedParkings((ParkingListData) msg)));
        } else if (msg.getClass().equals(ComplaintListData.class)) {
            System.out.format("i got some complaints update");
            Platform.runLater(() -> EventBus.getDefault().
                    post(new ReceivedComplaintsEvent((ComplaintListData) msg)));
        } else if (msg.getClass().equals(ReportListData.class)) {
            System.out.format("i got some reports update\n");
            Platform.runLater(() -> EventBus.getDefault().
                    post(new ReceivedReportsEvent((ReportListData) msg)));
        } else if (msg.getClass().equals(OrdersListData.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(new ReceivedOrderList((OrdersListData) msg)));
        } else if (msg.getClass().equals(EmployeeData.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(new EmployeeLoginReceivedEvent((EmployeeData) msg)));
        } else if (msg.getClass().equals(CostumerData.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(new CostumerLoginReceivedEvent((CostumerData) msg)));
        } else if (msg.getClass().equals(String.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(msg));
        } else if (msg.getClass().equals(PricesListToConfirm.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(new ReceivedConfirmaitnDataEvent((PricesListToConfirm) msg)));
        } else if (msg.getClass().equals(String.class)) {
            Platform.runLater(() -> EventBus.getDefault().
                    post(new String((String) msg)));
        } else {
            EventBus.getDefault().post(new MessageEvent((Message) msg));
        }
    }

    public void requestParkingLotList() {
        try {
            client.sendToServer("#request: parking lots list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestParkingPrices() {
        try {
            client.sendToServer("#request:parkingPrices");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestStastisticalInformationList() {
        try {
            client.sendToServer("#request:stastisticalinformationlist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changePrice(int price, int parkingLotId, String priceType) {
        try {
            client.sendToServer("#update:parking price," + parkingLotId + "," + priceType + "," + price);
            client.sendToServer("#request: prices table");
            App.setRoot("ParkingLotTable");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getParkings(String name) {
        try {
            System.out.println("sending " + name + "\n");
            client.sendToServer("#update:parkings," + name);
            //App.setRoot("ParkingScreenController");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void employeeLogin(String id, String password) {
        try {
            client.sendToServer("employee login:" + id + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendComplaint(ComplaintData complaint) {
        try {
            client.sendToServer(complaint);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeCompStatus(int compValue, int compId) {
        try {
            client.sendToServer("#update:complaint status," + compId + "," + compValue);
            client.sendToServer("#request: complaint table");
            App.setRoot("ComplaintsEmployee");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void costumerLogin(String id, String password) {
        try {
            client.sendToServer("costumer login:" + id + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void costumerRegister(String id, String password, String email) {
        try {
            client.sendToServer("costumer Register:" + id + "," + password + "," + email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submitReport(ReportData report) {
        try {
            client.sendToServer(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void approveChange(int id){
        try {
            client.sendToServer("#update:approve change," + id);
            client.sendToServer("#request: PricesToConfirm table");
            App.setRoot("PricesToConfirm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
